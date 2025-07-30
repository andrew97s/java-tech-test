package com.andrew.spring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.net.URI;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * 壁挂机摄像头对接服务，主要提供以下功能
 * 1. 视频预览（回放）
 * 2. 人脸识别（识别功能启停、事件推送）
 *
 * @author tongwenjin
 * @since 2025-2-14
 */

@Slf4j
@ServerEndpoint(value = "/roc/ws")
@Component
@SuppressWarnings("CallToPrintStackTrace")
public class CameraServer {

    public String fetchPlayUrl() {
        return "";
    }

    private Session clientSession;   // 本地客户端连接
    private Session serverSession;   // 代理连接的目标 WebSocket 服务器

    private static final String TARGET_WS_URL = "ws://192.168.1.88"; // 目标 WebSocket 服务器地址

    private static CountDownLatch latch; // 用来等待连接建立


    private WebSocketSession webSocketSession;


    @OnOpen
    public void onClientConnect(Session session) {
        this.clientSession = session;
        log.info("客户端已连接: {}", session.getId());

        try {
            latch = new CountDownLatch(1); // 等待目标 WebSocket 连接建立
            // 连接目标 WebSocket 服务器
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();

            // 设置缓冲区大小（例如 256 KB）
            container.setDefaultMaxBinaryMessageBufferSize(256 * 1024); // 二进制消息缓冲区
            container.setDefaultMaxTextMessageBufferSize(256 * 1024);   // 文本消息缓冲区

            // 创建自定义请求头配置
            ClientEndpointConfig.Configurator configurator = new ClientEndpointConfig.Configurator() {
                @Override
                public void beforeRequest(Map<String, List<String>> headers) {
                    headers.put("Sec-WebSocket-Protocol", Collections.singletonList("media"));
                }
            };

            ClientEndpointConfig config = ClientEndpointConfig.Builder.create()
                    .configurator(configurator)
                    .build();
            serverSession = container.connectToServer(new TargetWebSocketEndpoint(), config, new URI(TARGET_WS_URL));
            // 等待目标 WebSocket 连接
            latch.await();
            // 连接建立后，向目标服务器发送拉流消息
            sendToServer("{\"StreamId\": 1,\"Video\": true,\"Audio\": false,\"Smart\": false}");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnMessage
    public void onClientMessage(String message) {
        log.info("收到客户端消息: {}", message);
        sendToServer(message);
    }

    @OnClose
    public void onClientDisconnect(Session session) {
        log.info("客户端断开连接: {}", session.getId());
        closeServerSession();
    }

    @OnError
    public void onError(Session session, Throwable t) {
        try {
            if (!session.isOpen()) {
                log.error("session 连接发生异常,主动关闭连接!,异常信息:{}", t.getMessage());
            } else {
                log.error("Ws error occurred , msg : {}", t.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Failed to close ws connection , msg :{}", t.getMessage());
        }
    }

    private void sendToServer(String message) {
        if (serverSession != null && serverSession.isOpen()) {
            try {
                serverSession.getBasicRemote().sendText(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void closeServerSession() {
        try {
            if (serverSession != null) {
                serverSession.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 目标服务器 WebSocket 处理类
    public class TargetWebSocketEndpoint extends Endpoint {
        @Override
        public void onOpen(Session session, EndpointConfig config) {
            serverSession = session;

            // 目标服务器连接成功后，释放等待
            latch.countDown();

            // 监听目标服务器消息，并转发给客户端
            for (MessageHandler messageHandler : session.getMessageHandlers()) {
                session.removeMessageHandler(messageHandler);
            }
            session.addMessageHandler(String.class, message -> {
                log.info("收到目标服务器消息: {}", message);
                if (clientSession != null && clientSession.isOpen()) {
                    try {
                        clientSession.getBasicRemote().sendText(message);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            session.addMessageHandler(ByteBuffer.class, message -> {

                if (clientSession != null && clientSession.isOpen()) {
                    try {
                        clientSession.getBasicRemote().sendBinary(message);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            log.info("已连接目标 WebSocket 服务器: " + TARGET_WS_URL);
        }
        @Override
        public void onClose(Session session, CloseReason closeReason) {
            log.info("目标 WebSocket 服务器连接关闭:{}" , closeReason.getReasonPhrase());
            if (clientSession != null && clientSession.isOpen()) {
                try {
                    clientSession.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onError(Session session, Throwable throwable) {
            log.error("session error : {}" , throwable.getMessage());
        }
    }
}
