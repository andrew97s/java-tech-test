package com.andrew.java.juc.thread;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.concurrent.*;

/**
 * 线程异常测试
 *
 * 线程池异常处理：
 * 1. {@link ExecutorService#execute(Runnable)}方法会抛出任务内的异常
 * 2. {@link ExecutorService#submit(Runnable)}会吞掉任务内的异常并且封装在{@link Future}内，
 * 通过{@link Future#get()}获取结果时时会抛出对应任务内部的执行异常（{@link ExecutionException}）
 *
 * @author tongwenjin
 * @since 2023/8/15
 */
@Slf4j
public class ThreadExceptionTest {

    public static void main(String[] args) throws Exception {

//        newThreadTest();
//        newThreadPoolSubmitTest();
//        newThreadPoolExecuteTest();

        newForkJoinPoolTest();

        System.in.read();
    }

    private static void newThreadPoolSubmitTest() throws ExecutionException, InterruptedException {
        ThreadPoolExecutor pool = new ThreadPoolExecutor(
                1,
                2,
                10,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(100)
        );

        // submit 会返回 future对象 ， 异步执行抛出的异常会被封装， 通过 future.get()会抛出对应execution异常
        Future<?> fu = pool.submit(() -> {
            runWithException();
        });

        Object obj = fu.get();
    }

    private static void newThreadPoolExecuteTest() throws ExecutionException, InterruptedException {
        ThreadPoolExecutor pool = new ThreadPoolExecutor(
                1,
                2,
                10,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(100)
        );

        // execute 遗产直接会被抛出
        pool.execute(() -> {
            runWithException();
        });
    }

    private static void newForkJoinPoolTest() throws ExecutionException, InterruptedException {
        CompletableFuture<Void> fu = CompletableFuture.runAsync(() -> {
            runWithException();
        });
        fu.get();
    }


    private static void newThreadTest() {
        new Thread(()->{
            runWithException();
        }).start();
    }

    private static void runWithException() {
        log.info(" now starting !");

        if ( 1 == 1) {
            throw new RuntimeException("aa");
        }
    }
}
