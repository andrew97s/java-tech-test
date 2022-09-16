package com.andrew.mq.rabbitmq;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 *
 * RabbitMq 使用erLang 语言实现 ， 支持多语言异步通信 ， java 、python 、 php 、c# 、js
 *
 * 相较于activeMq ， 有着 文档全面 、 控制台UI易用、社区活跃 的特点
 * 实现了AMQP（Advanced Message Queuing Protocol ）高级消息队列协议 O-9-1 协议
 *
 * 官方文档 https://www.rabbitmq.com/tutorials/amqp-concepts.html
 *
 * 消息 基本分发原则 ：
 *      publisher -> exchange -> queue -> comsumer
 *      publisher: 消息生产者
 *      exchange: 消息路由器 ， 与queue构成rabbitMq 核心组件broker ， 通过队列的binding（绑定关系）将消息分发给指定队列
 *      queue: 存储消息的队列
 *      consumer：消息消费者
 *
 *  exchanger: 消息路由、分发器
 *      1.1 default exchange , 默认消息交换器（没有名字，为空字符串）
 *          直接把消息分发给绑定的队列（内部实现通过routingKey,队列名称分发至指定队列实现）
 *
 *      1.2 direct exchange , 直连消息交换器，通过消息携带的routingKey属性将消息发送给指定 routingKey 的queue
 *          (queue与exchange绑定是会定义对应的routingKey)
 *
 *      1.3 fanout exchange , 广播消息交换器 ， 发送到该交换器上的消息将会被发送到所有与之绑定的队列（默认忽略routingKey）
 *
 *      1.4 topic exchange , 话题消息交换器 ， 通过消息的routingKey 自动匹配与之绑定的队列的pattern进行匹配 对消息进行分发,
 *          因为pattern机制，具备较高的灵活性 ， 适合实现发布、订阅模型 、以及动态路由的场景
 *
 *      1.5 header exchange , 使用header进行路由匹配（忽略routingKey）
 *
 *  queue: 消息存储队列
 *      1.1 队列基本属性
 *          name: 名称, amq. 前缀的名称为内置消息队列使用保留
 *          durable: 队列持久化,broker重启后是否仍然存在
 *          exclusive: 是否为当前连接独享（connection） ,连接关闭后会自动删除
 *          auto-delete: 队列在最后一个消费者取消订阅后会被自动删除
 *          arguments: 消息额外的自定义属性
*
 *  binding: 绑定，exchange 与 queue的绑定关系
 *      1.1 default 、direct 、 topic exchange 都是通过routingKey 实现消息路由至指定queue
 *          fanout 全部转发到与之绑定的queue
 *          header 通过消息头路由到对应queue
 *
 *  consumer: 消息消费者
 *      消费者有两种消费模式：
 *          1. push API , 订阅消息（队列） ,默认模式
 *          2. pull API , 消费者手动抽取消息（效率非常低）
 *       每个消费者都一个consumer tag （一个字符串）可以用于取消订阅
 *
 *  message acknowledgement : 消息确认机制（basic.ack）
 *      auto-ack: 自动消息确认 ， consumer收到消息后会自动发送消息确认
 *      manual-ack: 手动消息确认 ， consumer 收到消息后需要手动发送消息确认
 *      一旦一个消费者获取消息后没有发送消息确认 ， broker会尝试将消息继续发送给其他的消费者（同一queue下）
 *
 *  message rejecting: 消息拒绝机制（通过basic.reject指令实现）
 *      当消费者处理消息失败时 ，可以发送broker当前消息消费失败的reject指令，来控制broker 删除这条消息或者重发当前消息
 *      （需要避免 单消费者 重发导致的死循环问题）
 *
 *  Nagetive Acknownledgement : rabbitMq 支持批量拒绝消息机制（通过basic.nack指令实现）
 *
 *  Message Arrtibutes and Payload: 消息的构成
 *      content-type: 消息类型
 *      content-encoding: 消息编码
 *      Routing key: 路由key(决定了消息 由exchange->queue的实现)
 *      Delivery mode: 消息传递模式
 *      Message priority: 消息优先级
 *      Message pulishing timestamp: 消息发布时间戳
 *      Expiration period: 消息过期周期
 *      Pulisher application id : 消息发布者ID
 *
 *      消息支持持久化机制（直接把消息推送到持久化的exchange 、 queue并不会使消息持久化）， 但会消耗一定的性能
 *
 *  Connection & Channel: 连接和通道
 *      connection : rabbitMq实现的AMQP-0-9-1协议使用的TCP实现可靠长连接 （断开连接应该避免tcp层断开，使用connection的API更为优雅 graceful）
 *      channel: rabbitMq 抽象出来的通道 ， 实际上使用的是同一个TCP连接（同一个Connection） , 同一connection下的不同channel逻辑上隔离，
 *      且每个channel拥有唯一的channelId
 *
 *  Virtual Hosts: 虚拟主机
 *      rabbitMq 通过virtual host 实现同一broker下创建多套环境 （不同环境的exchange 、queue互相隔离）
 *
 * 一、基本消费模型
 *  1. 点对点 单一消费
 *
 * @author tongwenjin
 * @since 2022/7/27
 */

@SuppressWarnings("all")
@Slf4j
public class RabbitMqTest {



    private static ConnectionFactory factory;

    private static Connection connection;

    private static Channel channel;

    final String directQueueName = "direct-queue-1";


    @BeforeAll
    public static void before() {
        factory = new ConnectionFactory();

        factory.setHost("tongwenjin.cloud");
        factory.setPort(5673);
        factory.setUsername("guest");
        factory.setPassword("guest");

        try {
            connection = factory.newConnection();
            channel = connection.createChannel();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        }
    }
    @AfterAll
    @SneakyThrows
    public static void after() {
        channel.close();
        connection.close();
    }

    @Test
    @SneakyThrows
    public void test1() {

        // 创建队列
        channel.queueDeclare(directQueueName, true, false, false, null);

        // 发送消息到指定队列
        channel.basicPublish("", directQueueName, null, "hello there !".getBytes(StandardCharsets.UTF_8));


        System.out.println("aa");
    }


    @Test
    @SneakyThrows
    public void test2() {
        channel.basicConsume(directQueueName,false,new DefaultConsumer(channel){
            /**
             * No-op implementation of {@link Consumer#handleDelivery}.
             *
             * @param consumerTag
             * @param envelope
             * @param properties
             * @param body
             */
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                log.info("New message comming  : {}" , new String(body));
                log.info("deliveryTag : {}" , envelope.getDeliveryTag());
                getChannel().basicAck(envelope.getDeliveryTag() , false);
            }

        });

        System.in.read();
    }


    @Test
    @SneakyThrows
    public void test3() {

        AMQP.BasicProperties props = new AMQP.BasicProperties.Builder()
//                .expiration("3000")
                .build();


        channel.basicPublish(
                "",
                "direct-queue-2",
                props,
                "to test the dead letter !".getBytes(StandardCharsets.UTF_8)
        );

    }


    @Test
    @SneakyThrows
    public void test4() {
        channel.basicConsume("dead-queue-1",false,new DefaultConsumer(channel){
            /**
             * No-op implementation of {@link Consumer#handleDelivery}.
             *
             * @param consumerTag
             * @param envelope
             * @param properties
             * @param body
             */
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                log.info("New message comming  : {}" , new String(body));
                log.info("deliveryTag : {}" , envelope.getDeliveryTag());
                getChannel().basicAck(envelope.getDeliveryTag() , false);
            }

        });

        System.in.read();
    }

    @Test
    @SneakyThrows
    public void test5() {
        channel.basicConsume("direct-queue-2",false,new DefaultConsumer(channel){
            /**
             * No-op implementation of {@link Consumer#handleDelivery}.
             *
             * @param consumerTag
             * @param envelope
             * @param properties
             * @param body
             */
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                log.info("New message comming  : {}" , new String(body));
                log.info("deliveryTag : {}" , envelope.getDeliveryTag());
                getChannel().basicNack(envelope.getDeliveryTag() , false,true);
            }

        });

        System.in.read();
    }
}
