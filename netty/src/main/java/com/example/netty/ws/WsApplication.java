package com.example.netty.ws;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.yeauty.annotation.EnableWebSocket;

/**
 * @author tongwenjin
 * @since 2022/7/11
 */

@SpringBootApplication
@EnableWebSocket
public class WsApplication {

    public static void main(String[] args) {
        SpringApplication.run(WsApplication.class , args);
    }


}
