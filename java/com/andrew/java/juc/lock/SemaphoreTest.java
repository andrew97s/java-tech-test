package com.andrew.java.juc.lock;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.Semaphore;

/**
 * @author tongwenjin
 * @since 2022/7/6
 */

@Slf4j
@SuppressWarnings("all")
public class SemaphoreTest {

    @Test
    @SneakyThrows
    public void testSemaphore() {
        // 信号量 对指定资源进行限制 同时只有指定数量的线程能获得锁对象 （类似与令牌桶 、 漏斗算法 -流量控制算法）
        Semaphore semaphore = new Semaphore(1 , false);


        semaphore.acquire();

        for (int i = 0; i < 2; i++) {
            final long sleep  = i*1000;
            new Thread(()->{
//                for (;;)  {


                try {
                    Thread.sleep(sleep);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                    // acquire 获取锁对象 ，
                    // tryAcquire
                    // 公平锁 需要判断当前锁对象是否有排队时间比当前线程更长的线程 , 非公平锁不需要判断是否有排队线程
                    // 所谓的公平 与非公平 指的是 当前线程去竞争锁资源的时候 如果存在其他更早排队竞争的线程
                    // 公平锁会在 tryAcquire 直接返回false 继而进入队列 按序竞争锁资源
                    // 非公平锁 如果存在可竞争锁资源 可以直接参与竞争锁资源 , 不存在可竞争锁资源 ， 则同公平锁一样进入队列按照队列顺序获得锁资源

                    // 尝试获取锁资源时 锁对象不存在可用锁资源 公平锁与非公平锁行为一致 都是进入队列按序获取锁资源
                    // 锁对象存在可用锁资源时 非公平锁 直接尝试获取锁资源 ，公平锁 需要进入队列按序获取锁资源
                    try {

                        semaphore.acquire();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    log.info(" acquried !");
                    try {
                        Thread.sleep(1000L);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
//                    semaphore.release();
//                }

            }).start();
        }

        System.in.read();

        semaphore.release();



    }
}
