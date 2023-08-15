package com.andrew.java.core;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.*;

/**
 * 测试URL
 *
 * @author tongwenjin
 * @since 2023/8/11
 */
@Slf4j
public class UrlTest {
    public static void main(String[] args) throws IOException {
        String url = "http://www.google.com";
        connectDirectly(url);
        connectWithProxy(url);
    }

    private static void connectDirectly(String urlStr) throws IOException {
        URL url = new URL(urlStr);

        // 通过本地代理连接
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        connection.setConnectTimeout(3000);

        try {
            connection.connect();
        } catch (SocketTimeoutException e) {
           log.error("socket 连接超时");
           return;
        }

        log.info("direct 连接返回的响应结果:{}" ,connection.getResponseMessage() );
    }

    private static void connectWithProxy(String urlStr) throws IOException {
        URL url = new URL(urlStr);

        // 通过本地代理连接
        HttpURLConnection connection = (HttpURLConnection)url.openConnection(
                new Proxy(
                        Proxy.Type.HTTP,
                        new InetSocketAddress("127.0.0.1",10809)
                )
        );
        connection.setConnectTimeout(3000);

        connection.connect();

        log.info("proxy 连接返回的响应结果:{}" ,connection.getResponseMessage() );
    }
}
