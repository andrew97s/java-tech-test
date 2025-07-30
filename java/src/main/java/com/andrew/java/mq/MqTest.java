package com.andrew.java.mq;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.nio.charset.StandardCharsets;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author tongwenjin
 * @since 2025-5-20
 */
public class MqTest {


    private static final String HOST = "192.168.1.102";
    private static final int PORT = 5672;
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "Pa33w0rd";
    private static final String VIRTUAL_HOST = "/";
    private static final String EXCHANGE_NAME = "monitor.src.upload";

    public static void main(String[] args) throws Exception {
        // 创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(HOST);
        factory.setPort(PORT);
        factory.setUsername(USERNAME);
        factory.setPassword(PASSWORD);
        factory.setVirtualHost(VIRTUAL_HOST);

        // 创建连接和通道
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        // 可选：声明交换机（如果还没创建）
        channel.exchangeDeclare("monitor.src.link", BuiltinExchangeType.FANOUT, true);

        // 定时任务：每 5 秒发送一条消息
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            int count = 1;

            @Override
            public void run() {
                String message = "{\"event\":\"heartbeat\",\"facility\":{\"addrStr\":\"00000007231B48533936330F32393932\",\"descr\":\"\",\"facilities\":\"传输设备\",\"facilitiesCode\":0,\"facilitiesType\":\"传输设备\",\"facilitiesTypeCode\":128,\"isWireless\":false,\"net\":\"00000007231B48533936330F32393932\"},\"stat\":[],\"treat\":{\"treatType\":99,\"treatTypeStr\":\"心跳\"}}\n";
                try {
                    channel.basicPublish("monitor.src.link", "", null, message.getBytes(StandardCharsets.UTF_8));
                    System.out.println(" [x] Sent: '" + message + "'");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 0, 10); // 每 5000ms 运行一次

        Channel channel1 = connection.createChannel();

        // 可选：声明交换机（如果还没创建）
        channel1.exchangeDeclare("monitor.src.upload", BuiltinExchangeType.FANOUT, true);

        // 定时任务：每 5 秒发送一条消息
        Timer timer1 = new Timer();
        timer1.scheduleAtFixedRate(new TimerTask() {
            int count = 1;

            @Override
            public void run() {
                String message = "{\"event\":\"alarm\",\"facility\":{\"addrSrc\":1001061,\"addrStr\":\"1机 1-61\",\"descr\":\"机舱温感感温\",\"facilities\":\"现场部件\",\"facilitiesCode\":2,\"facilitiesType\":\"点型感温火灾探测器\",\"facilitiesTypeCode\":31,\"isWireless\":false,\"net\":\"00000007231B48533936330F32393932\"},\"stat\":[{\"isTreated\":0,\"time\":1747714688,\"treatType\":0,\"type\":6,\"typeStr\":\"故障\",\"val\":7,\"valStr\":\"故障\"}],\"treat\":{\"treatType\":0,\"treatTypeStr\":\"未处警\"}}";
                try {
                    channel.basicPublish("monitor.src.upload", "", null, message.getBytes(StandardCharsets.UTF_8));
                    System.out.println(" [x] Sent: '" + message + "'");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 0, 10); // 每 5
    }
}
