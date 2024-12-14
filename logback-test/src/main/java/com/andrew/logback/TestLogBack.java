package com.andrew.logback;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * to test logback !
 *
 * @author andrew
 * @since 2022/9/10
 * */
public class TestLogBack {

    private static final Logger logger = LoggerFactory.getLogger(TestLogBack.class);


    public static void main(String[] args) throws InterruptedException {
        while (true) {
            Thread.sleep(1000L);

            logger.info("logger info : {}" , System.currentTimeMillis());
        }
    }
}
