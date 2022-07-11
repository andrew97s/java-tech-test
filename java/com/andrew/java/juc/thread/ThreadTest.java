package com.andrew.java.juc.thread;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * @author tongwenjin
 * @since 2022/7/6
 */

@Slf4j
@SuppressWarnings("all")
public class ThreadTest {

    @Test
    @SneakyThrows
    public void testThreadInterrupt() {
        // jdk sleep() 方法已经实现了interrupt 指令发生后退出 睡眠状态并抛出interruptedException的机制
        Thread thread = new Thread(() -> {
            log.info("Begin to sleep fot 10 seconds !");

            try {
                Thread.sleep(10000L);
            } catch (InterruptedException e) {
                log.info("Interrupted for : {}!", e.getMessage());
            }
        });

        thread.start();

        thread.interrupt();
    }


    @Test
    @SneakyThrows
    public void testThreadInterruptLoop() {

        // interrupt 会给指定线程发送一个中断信号
        // 默认JDK  已经实现了 wait sleep io 等操作的自动中断线程的实现 （抛出 interruptedException）
        // 针对自己的业务可以 通过判断线程是否被发送中断指令（isInterrupted()方法）可以自定义实现线程某个状态的中断 ！
        Thread thread = new Thread(() -> {
            for (;;) {
                if ((System.currentTimeMillis() + "").endsWith("000")) {
                    log.info("I'm still alive !");
                } else if (Thread.currentThread().isInterrupted()) {
                    log.info("I'm interrupted !");
                    break;
                }
            }

        });

        thread.start();

        // 10s 后发送中断线程指令
        Thread.sleep(10000L);

        thread.interrupt();
    }
}
