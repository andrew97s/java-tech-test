//package com.andrew.java.jdk8;
//
//import lombok.SneakyThrows;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.Test;
//
//import java.util.concurrent.CompletableFuture;
//import java.util.concurrent.Executors;
//
///**
// * @author tongwenjin
// * @since 2022/7/23
// */
//
//@Slf4j
//@SuppressWarnings("all")
//public class CompletableFutureTest {
//
//    public static void main(String[] args) {
//
//    }
//
//
//    @Test
//    @SneakyThrows
//    public void test1() {
//        CompletableFuture<String> future = new CompletableFuture<>();
//
//        new Thread(() -> {
//            try {
//                Thread.sleep(3000L);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//
//            future.complete("Fininshed !");
//        }).start();
//
//        // get方法会阻塞当前方法直到 返回结果
//        log.info(future.get());
//    }
//
//
//    @Test
//    @SneakyThrows
//    public void test2() {
//        // runAsync 方法 支持异步处理 无返回值
//        CompletableFuture future = CompletableFuture.runAsync(() -> {
//            try {
//                Thread.sleep(3000L);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//
//            log.info("Finished !");
//        });
//
//        System.out.println(future.get());
//    }
//
//
//    @Test
//    @SneakyThrows
//    public void test3() {
//        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
//            log.info("will return in 3 seconds !");
//            try {
//                Thread.sleep(3000L);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//
//            return "Finished !";
//        });
//
//        System.out.println(future.get());
//    }
//
//
//    @Test
//    @SneakyThrows
//    public void test4() {
//        // completableFuture 支持自定义线程池 ， 默认使用ForkjoinPool.commonPool
//        CompletableFuture.runAsync(
//                () -> {
//                    log.info("Use custom pool rather than ForkjoinPool.commonPool !");
//                },
//                Executors.newSingleThreadExecutor()
//        );
//    }
//
//
//    @Test
//    @SneakyThrows
//    public void test5() {
//        // thenApply 方法用于处理上一次异步执行的结果 ， 最后一个thenApply 将返回future最终结果
//        CompletableFuture future = CompletableFuture.supplyAsync(() -> {
//            log.info("First process will be last for 1 second !");
//            try {
//                Thread.sleep(1000L);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//
//            return "First result !";
//        }).thenApply((result) -> {
//            log.info("Receive the first process result is {} , and secons process will be last for 2 seconds !", result);
//
//            try {
//                Thread.sleep(2000L);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//
//            return "Second result !";
//        }).thenApply((result) -> {
//            log.info("Receive the seconds process result is {} , and third process will be last for 3 seconds !", result);
//
//            try {
//                Thread.sleep(3000L);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//
//            return 3;
//        });
//
//        log.info("Get the future result : {}", future.get());
//    }
//
//
//    @Test
//    @SneakyThrows
//    public void test6() {
//
//        // thenAceept 会消耗掉future的结果 ， 导致其他线程尝试get获取future结果为空
//        CompletableFuture future = CompletableFuture.supplyAsync(() -> {
//            return "aa";
//        }).thenAccept((result) -> {
//            log.info("get the result");
//        });
//
//        log.info("Get the future result : {}", future.get());
//    }
//
//
//    @Test
//    @SneakyThrows
//    public void test7() {
//        // thenRun 不需要future的结果 而进行的任务处理
//        CompletableFuture future = CompletableFuture.supplyAsync(() -> {
//            return "bb";
//        }).thenRun(() -> {
//            log.info("Future's  process without future's result !");
//        });
//
//        log.info("Get the future's  result : {}", future.get());
//    }
//
//
//    @Test
//    @SneakyThrows
//    public void test8() {
//
//        // thenCompose 用于将多个future 合成一个future处理
//        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
//            return "1";
//        });
//
//        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> {
//            return 2;
//        });
//
//        System.out.println(future1.thenCompose(result -> future2).get());
//    }
//
//
//    @Test
//    @SneakyThrows
//    public void test9() {
//
//        // thenCombine 用户将另一个future的结果与当前future结果 进行联合 组成一个新的future
//        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> 1);
//        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> 2);
//
//        System.out.println(future1.thenCombine(future2, (future1Result, future2Result) -> {
//            return future1Result + future2Result;
//        }).get());
//    }
//
//
//    @Test
//    @SneakyThrows
//    public void test10() {
//        // allOf 可以指定多个future完成后 当前future对象执行的操作
//        CompletableFuture future1 = CompletableFuture.supplyAsync(() -> {
//            try {
//                Thread.sleep(1000L);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//            log.info("Future1 complete !");
//
//            return 1;
//        });
//
//        CompletableFuture future2 = CompletableFuture.supplyAsync(() -> {
//            try {
//                Thread.sleep(2000L);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//            log.info("Future2 complete !");
//
//            return 1;
//        });
//
//        CompletableFuture future3 = CompletableFuture.supplyAsync(() -> {
//            try {
//                Thread.sleep(3000L);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//            log.info("Future3 complete !");
//            return 1;
//        });
//
//
//        CompletableFuture.supplyAsync(() -> 0).allOf(future1, future2, future3).thenApply((result) -> {
//            log.info(" finllay complete !");
//
//            return 1;
//        });
//
//        System.in.read();
//    }
//
//
//    @Test
//    @SneakyThrows
//    public void test11() {
//        CompletableFuture<Void> future1 = CompletableFuture.runAsync(() -> {
//            try {
//                Thread.sleep(1000L);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//
//            log.info("future1 complete !");
//        });
//
//        CompletableFuture<Void> future2 = CompletableFuture.runAsync(() -> {
//            try {
//                Thread.sleep(2000L);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//
//            log.info("future2 complete !");
//        });
//
//        CompletableFuture<Void> future3 = CompletableFuture.runAsync(() -> {
//            try {
//                Thread.sleep(3000L);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//
//            log.info("future3 complete !");
//        });
//
//
//        CompletableFuture.anyOf(future1, future2, future3).thenRun(() -> {
//            log.info("Finally complete !");
//        });
//
//        System.in.read();
//    }
//
//
//    @Test
//    @SneakyThrows
//    public void test12() {
//        // exceptionally 可以捕捉上一个future引用的异常并作出处理
//        CompletableFuture<Integer> future = CompletableFuture
//                .supplyAsync(() -> {
//                    log.info("supply 1");
//                    System.out.println(1 / 0);
//                    return 1;
//                })
//                .exceptionally((e) -> {
//                    log.error("oops , exception caught : {}", e.getMessage());
//
//                    return -1;
//                })
//                .thenApply((result) -> {
//                    log.info("supply 2");
//
//
//                    return 1 + result;
//                }).thenApply((result) -> {
//                    log.info("supply 3");
//
//                    return 1 + result;
//                }).thenApply(result -> {
//                    log.info("final result : {}", result);
//
//                    return result + 1;
//                });
//
//        System.out.println(future.get());
//    }
//
//
//    @Test
//    @SneakyThrows
//    public void test13() {
//        // handle 可以汇总 处理future结果 ， 异常会被吞掉
//        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
//            return -1;
//        }).thenApply(result -> {
//            return result + 1;
//        }).thenApply(result -> {
//            System.out.println(1 / 0);
//            return result + 1;
//        }).handle((result, e) -> {
//            log.error("Oops exception caught : {}", e.getMessage());
//            return  - 1;
//        });
//
//        System.out.println(future.get());
//    }
//}
