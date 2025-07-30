package com.zhian;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * The type Net server.
 *
 * @author tongwenjin
 * @since 2025 -6-25
 */
@Slf4j
public class NetServer {
    private static final String TOPIC = "/v1/bydl/consume/monitor";

    private static MqttClient client;

    private static String mqttIp;
    private static String mqttPort;

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     * @throws IOException the io exception
     */
    public static void main(String[] args) throws IOException {
        // 创建 HttpServer 绑定到 9200 端口
        HttpServer server = HttpServer.create(new InetSocketAddress("0.0.0.0", 9200), 0);

        // 创建上下文，路径为 /
        server.createContext("/", new JbHttpHandler());

        // 启动线程池执行（也可以使用默认 executor）
        server.setExecutor(null); // 默认 executor
        server.start();

        // 连接MQTT
        if (args == null || args.length < 2) {
            log.error("启动失败,参数不能为空!");
            System.exit(1);
        }
        try {
            startMQtt(args[0], args[1]);
        } catch (Exception e) {
            log.error("启动失败,MQtt连接失败!");
            System.exit(1);
        }

        HeartKeeper.start();

        log.info("HTTP Server started on port 9200!");
    }

    private static void startMQtt(String ip, String port) throws MqttException {
        String url = "tcp://" + ip + ":" + port;
        NetServer.mqttIp = ip;
        NetServer.mqttPort = port;
        log.info("准备连接MQtt: {}", url);
        String clientId = "client-" + UUID.randomUUID();

        client = new MqttClient(url, clientId);

        MqttConnectOptions options = new MqttConnectOptions();
        options.setAutomaticReconnect(true);
        options.setCleanSession(true);
        options.setConnectionTimeout(10);
        // 每 30 秒发送心跳
        options.setKeepAliveInterval(30);

        client.connect(options);
        log.info("MQtt 已连接: " + url);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                client.disconnect();
                client.close();
                log.info("MQTT 成功断开连接.");
            } catch (MqttException e) {
                e.printStackTrace();
            }
        }));
    }

    static class JbHttpHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String response = "{\"code\":200,\"msg\":\"ok\"}";
            exchange.sendResponseHeaders(200, response.getBytes().length);

            String method = exchange.getRequestMethod();
            String path = exchange.getRequestURI().getPath();
            String query = exchange.getRequestURI().getQuery();
            log.info("request arrived: {}", path);

            // 读取请求体
            if (method.equals("POST")) {
                try (InputStream is = exchange.getRequestBody()) {
                    String requestBody = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))
                            .lines()
                            .reduce("", (acc, line) -> acc + line);
                    log.info("request body : {}", requestBody);

                    // 处理消息
                    processMsg(requestBody);
                }
            }

            // 响应
            try (OutputStream os = exchange.getResponseBody();) {
                os.write(response.getBytes());
            }
        }
    }

    private static void processMsg(String source) {
        if (StrUtil.isBlank(source)) {
            return;
        }

        List<PushVO> pushVOs = extractPushVO(source);
        if (CollUtil.isEmpty(pushVOs)) {
            return;
        }

        try {
            pushMqtt(JSON.toJSONString(pushVOs));
            log.info("mqtt msg: {}", JSON.toJSONString(pushVOs));
        } catch (Exception e) {
            log.error("推送MQtt发生异常:{}", e.getMessage());
        }
    }

    public static void pushMqtt(String payload) throws MqttException {
        if (client.isConnected()) {
            MqttMessage msg = new MqttMessage(payload.getBytes());
            msg.setQos(1);
            client.publish(TOPIC, msg);
        } else {
            log.error("推送MQtt消息失败,连接已断开!");
            // 尝试重连
            startMQtt(mqttIp, mqttPort);
            if (client.isConnected()) {
                log.info("MQtt重连成功!");
                MqttMessage msg = new MqttMessage(payload.getBytes());
                msg.setQos(1);
                client.publish(TOPIC, msg);
                log.info("推送MQtt消息成功: " + payload);
            } else {
                log.error("MQtt重连失败!");
            }
        }
    }

    private static List<PushVO> extractPushVO(String jbSourceStr) {
        // 青鸟推送告警格式解析
        JbSourceVO jbSource = JSON.parseObject(jbSourceStr, JbSourceVO.class);
        JbSourceVO.Facility facility = jbSource.getFacility();

        List<PushVO> result = new ArrayList<>();

        if (CollUtil.isEmpty(jbSource.getStat()) || !"alarm".equals(jbSource.getEvent())) {
            return result;
        }

        // 推送格式转义
        for (JbSourceVO.Stat stat : jbSource.getStat()) {
            PushVO pushVO = new PushVO();
            JbStatType jbStat = JbStatType.loadByCode(stat.getType());
            String addressCode = addressFormat(facility.getAddrStr());
            // 用传离线
            if (facility.getAddrStr().equals(facility.getNet()) && stat.getValStr().contains("离线")) {
                log.info("用传已离线!");
                HeartKeeper.isNetOffline = true;
                continue;
            }
            // 用传在线
            if (StrUtil.isNotBlank(facility.getNet())) {
                HeartKeeper.isNetOffline = false;
            }
            // 设备离线
            if (stat.getValStr().contains("离线")) {
                log.info("设备({})已离线!", addressCode);
                HeartKeeper.setOffline(addressCode);
                continue;
            } else {
                HeartKeeper.removeOffline(addressCode);
            }
            // 主机复位
            if (facility.getAddrStr().endsWith("机") && stat.getValStr().contains("复位")) {
                String controllerCode = facility.getAddrStr().replace("机", "");
                HeartKeeper.resetByController(controllerCode);
                continue;
            }
            // 仅处理青鸟火警事件
            if (jbStat.getCode() != 1) {
                log.warn("非火警类型青鸟事件,忽略!");
                continue;
            }

            Date date = new Date(stat.getTime() * 1000);
            // 时间
            pushVO.setTimestamp(DateUtil.formatDateTime(date));
            // 设备地址(网关 + 设备一次码)
            pushVO.setCddzm(addressCode);
            // 事件描述
            pushVO.setDescription(
                    facility.getFacilitiesType() +
                            "(备注:" + (StrUtil.isBlank(facility.getDescr()) ? "" : facility.getDescr()) + ")" +
                            " 发生了 " + jbStat.getName() + "事件(类型:" + stat.getValStr() + ")"
            );
            // 告警级别  0：正常；1：注意；2：异常；3：严重
            pushVO.setGjjb("-1");

            pushVO.setZdbh("");
            pushVO.setZzbh("");
            pushVO.setTp("");

            // 检测值取事件类型(火警类取1  )
            if (stat.getValStr().contains("撤销")) {
                // 火警撤销
                HeartKeeper.resetFire(addressCode);
                pushVO.setCddzm("0");
            } else {
                HeartKeeper.setFire(addressCode);
                pushVO.setCdz("1");
            }
            result.add(pushVO);
        }

        return result;
    }

    public static String addressFormat(String input) {
        // 去除所有空格
        input = input.replaceAll("\\s+", "");

        // 正则提取：例如 4机1-1 -> 4, 1, 1
        return input.replaceAll("(\\d+)机(\\d+)-(\\d+)", "$1-$2-$3");
    }
}
