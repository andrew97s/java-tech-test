package com.example.netty.ws.server;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.yeauty.annotation.OnBinary;
import org.yeauty.annotation.OnClose;
import org.yeauty.annotation.OnError;
import org.yeauty.annotation.OnEvent;
import org.yeauty.annotation.OnMessage;
import org.yeauty.annotation.OnOpen;
import org.yeauty.annotation.PathVariable;
import org.yeauty.annotation.ServerEndpoint;
import org.yeauty.pojo.Session;

/**
 * @author tongwenjin
 * @since 2022/7/11
 */

@ServerEndpoint(
        port = "88",
        path = "/ws/{id}",
        eventExecutorGroupThreads = "1",
        bossLoopGroupThreads = "1",
        workerLoopGroupThreads = "1"
)
@Slf4j
@Component
public class WsServer {

    @OnMessage
    public void handleMessage(String message) {
        log.info("Message coming ! : {}" , message);
    }

    @OnOpen
    public void handleOpen(
            Session session,
            @PathVariable String id
    ) {
        log.info("New channel opened (id : {})!" , id);
    }

    @OnClose
    public void handleClose(Session session) {
        log.info("Channel closed !");
    }

    @OnEvent
    public void handleEvent(Session session) {
        log.info("Channel event occurred !");
    }

    @OnError
    public void handleError(Session session) {
        log.info("Chanel error occurred !");
    }

    @OnBinary
    public void handleBinary(Session session) {
        log.info("New binary message coming !");
    }
}
