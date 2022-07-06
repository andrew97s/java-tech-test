package com.andrew.java;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;

/**
 * @author tongwenjin
 * @since 2022/7/5
 */

@Slf4j
@SuppressWarnings("all")
public class CountDownLatchTest {

    @Test
    @SneakyThrows
    public void testLatch() {
        CountDownLatch latch = new CountDownLatch(10);

        // 计数锁

        for (int i = 0; i < 10; i++) {
            final long sleepSecs = i;
            new Thread(()->{
                try {
                    Thread.sleep(sleepSecs*1000L);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                // countDown 方法直接调用AQS CAS 实现 state - 1
                latch.countDown();
                try {


                    // await 首先判断 AQS 的 state = 0  , 是 则直接返回
                    // 否 当前线程加入AQS 等待队列 直至 state = 0 被唤醒
                    latch.await();

                    // awaitTimeOut 通过Unsafe.parkNanos() 实现 且默认有1seconds 的自旋时间
                    // latch.await(10 , TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                log.info("Latch reached !");
            }).start();
        }

        System.in.read();
    }

    private static Object object = new Object();

}
