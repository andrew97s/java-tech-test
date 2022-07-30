package com.andrew.mq.rabbitmq;

import com.rabbitmq.client.AMQP;
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
 * 消息 基本分发原则 ：
 *      publisher -> exchange -> queue -> comsumer
 *      publisher: 消息生产者
 *      exchange: 消息路由器
 *      queue: 存储消息的队列
 *      consumer：消息消费者
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
}
