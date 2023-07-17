package com.andrew.java.juc.lock;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author tongwenjin
 * @since 2022/7/6
 */

@SuppressWarnings("all")
@Slf4j
public class CyclicBarrierTest {

    @Test
    @SneakyThrows
    public void test1() {
        // 循环计数屏障 ， 多个线程使用await()达到 指定屏障突破值时 会触发该锁对象的所有await()方法 恢复执行
        CyclicBarrier barrier = new CyclicBarrier(2, () -> log.info("Finally Barrier breaked !"));

        for (int i = 0; i < 1; i++) {
            final long sleep = (i == 0 ? 0 : 10000L);
            new Thread(() -> {
                try {
//                    Thread.sleep(sleep);
                    barrier.await();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } catch (BrokenBarrierException e) {
                    throw new RuntimeException(e);
                }

                log.info("Barrier breaked !");
            }).start();
        }

        System.in.read();
    }

    @Test
    @SneakyThrows
    public void test2() {
        // AQS 的condition 的 await()方法 会导致该AQS 释放锁资源 ， 而其他线程可以获得当前AQS 的锁对象
        CyclicBarrier barrier = new CyclicBarrier(2, () -> log.info("Finally Barrier breaked !"));

        Field lockField = CyclicBarrier.class.getDeclaredField("lock");
        lockField.setAccessible(true);

        ReentrantLock lock = (ReentrantLock)lockField.get(barrier);
        Condition condition = lock.newCondition();

        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                lock.lock();
                log.info("Locked !");
                try {
                    condition.await();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }).start();
        }


        System.in.read();
    }
}
