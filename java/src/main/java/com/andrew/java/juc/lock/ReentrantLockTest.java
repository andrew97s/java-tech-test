package com.andrew.java.juc.lock;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author tongwenjin
 * @since 2022/7/4
 */


@Slf4j
@SuppressWarnings("all")
public class ReentrantLockTest {

    @SneakyThrows
    public static void main(String[] args) {
        // AQS 排他锁（锁对象只能被一个线程持有）
        // 支持可重入 ， 支持公平 、 非公平
        ReentrantLock lock = new ReentrantLock(true);

        ExecutorService pool = Executors.newFixedThreadPool(1);

        for (int i = 0; i < 2; i++) {
            pool.submit(()->{
                lock.lock();

                log.info("locked");

                lock.unlock();

                log.info("unlocked ");

//                lock.unlock();
//                System.out.println(pool);
            });
        }

        System.in.read();
    }


    @Test
    @SneakyThrows
    public void testInterrupt() {
        new Thread(()->{
            log.info("Start interrupt !");
            Thread.currentThread().interrupt();
            log.info("Finished interrupt !");
        }).run();

        System.in.read();
    }

    @Test
    @SneakyThrows
    public void testLockSupport() {
        Thread t1 = new Thread(() -> {
            for (; ; ) {
                for (int i = 0; i < 10; i++) {
                    log.info("I will be park in {} seconds !", 10-i);
                    try {


                        Thread.sleep(1000L);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }

                LockSupport.park(this);
            }
        });
        t1.start();


        Thread.sleep(1000000L);

        log.info("Try to unpark t1 !");

        LockSupport.unpark(t1);

        log.info("T1 successfully unparked !!");


        System.in.read();

    }

    private int value = 0;

    private static Unsafe unsafe ;

    private static long valueOffset = 0;

    static {
        Field field = null;
        try {
            field = Unsafe.class.getDeclaredField("theUnsafe");

            field.setAccessible(true);
            unsafe =(Unsafe) field.get(null);
            valueOffset = unsafe.objectFieldOffset(ReentrantLockTest.class.getDeclaredField("value"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    @SneakyThrows
    public void testCAS() {


        CountDownLatch latch = new CountDownLatch(100);

        for (int i = 0; i < 100; i++) {
            new Thread(()->{
                boolean b = unsafe.compareAndSwapInt(this, valueOffset, 0, 1);
                log.info("CAS {}" , b ? "passed" : "failed");
                latch.countDown();
            }).start();
        }


        latch.await();
        System.out.println(value);
    }


    @Test
    @SneakyThrows
    public void testAQSUnlock() {
        ReentrantLock lock = new ReentrantLock();

        new Thread(()->{
            lock.lock();
            log.info("lock now is locked !");

            try {
                Thread.sleep(10000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            lock.unlock();

            log.info("lock is successfully unlocked !");
        }).start();

        new Thread(()->{
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.info("try to lock !");

            // lock 方法如过当前锁没有持有对象 即可获得锁对象 无需通过AQS 内node节点
            // 公平锁和非公平锁 在 tryAcquire 方法时 公平锁 需要判断是否有排队的其他线程 而非公平锁则不需要判断
            //
            // 否则
            lock.lock();

            log.info("lock is unlocked !");
        }).start();



        System.in.read();
    }

    @Test
    @SneakyThrows
    public void testAQSFairness() {

        ReentrantLock lock;
        ReentrantLock fairLock = new ReentrantLock(true);
        ReentrantLock unFairLock = new ReentrantLock(false);

        lock = fairLock;

        lock.lock();
        for (long i = 0; i < 10L; i++) {
            final long sleepMinis = i;
            new Thread(()->{
                try {
                    Thread.sleep(sleepMinis * 1000);
                }catch (Exception e) {
                    e.printStackTrace();
                }
                lock.lock();
                log.info("Locked !!");
                lock.unlock();
            }).start();
        }

        Thread.sleep(10000L);
        lock.unlock();

        lock.lockInterruptibly();

        System.in.read();
    }
}
