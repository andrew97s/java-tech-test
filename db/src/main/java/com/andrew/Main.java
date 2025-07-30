package com.andrew;

import cn.hutool.core.util.IdUtil;
import cn.hutool.db.DbUtil;
import cn.hutool.db.Entity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The type Main.
 */
public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws Exception {
        doTruncate();
        // SQLite 数据库路径 "jdbc:sqlite:file:C:\\Users\\Administrator\\Desktop\\license\\fire.sqlite"
        String url = "jdbc:sqlite:file:C:\\Users\\Administrator\\Desktop\\license\\fire.sqlite?journal_mode=WAL&busy_timeout=0";
//        String url = "jdbc:sqlite:file:C:\\Users\\Administrator\\Desktop\\license\\fire.sqlite";

        // 创建一个线程池来模拟并发操作
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        // 线程1：持有数据库连接进行长时间的写操作
        executorService.submit(() -> {
            try (Connection conn = DriverManager.getConnection(url)) {
                conn.setAutoCommit(false);
                Thread.sleep(500);
                Statement stmt = conn.createStatement();
                System.out.println("线程1: 开始写操作...");
                stmt.executeUpdate("insert into za_sys_message (id, pf_code, device_id, device_code, type, content, create_time, results) \n" +
                        "values (\n" +
                        "        "+IdUtil.getSnowflakeNextIdStr() +","+IdUtil.getSnowflakeNextIdStr()+",'xx1','xxx1','1','1',datetime(),1\n" +
                        "       )");
                // 模拟长时间的事务
                Thread.sleep(5000);
                System.out.println("线程1: 完成写操作...");
                conn.commit();
            } catch (SQLException | InterruptedException e) {
                e.printStackTrace();
            }
        });

        // 线程2：尝试进行写操作，会遇到锁定异常
        executorService.submit(() -> {

            try (Connection conn = DriverManager.getConnection(url)) {
                conn.setAutoCommit(false);
                Thread.sleep(1000);
                Statement stmt = conn.createStatement();
                System.out.println(new Date().toLocaleString() + "线程2: 尝试写操作...");
                // 在第一个线程的事务持有锁的情况下执行写操作
                try {
                    stmt.executeUpdate("insert into za_sys_message (id, pf_code, device_id, device_code, type, content, create_time, results) \n" +
                            "values (\n" +
                            "        "+IdUtil.getSnowflakeNextIdStr() +","+IdUtil.getSnowflakeNextIdStr()+",'xx1','xxx1','1','1',datetime(),1\n" +
                            "       )");
                    System.out.println("线程2:完成写操作");
                    conn.commit();
                } catch (Exception e) {

                    // 捕获锁定异常
                    if (e.getMessage().contains("database is locked")) {
                        System.out.println(new Date().toLocaleString() + "线程2: 锁定异常 - 数据库被锁定");

                        int tryCount = 0;
                        while (true) {
                           try {
                               Thread.sleep(500);
                               System.out.println("线程2:第"+ ++tryCount +"次尝试...");
                               stmt.executeUpdate("insert into za_sys_message (id, pf_code, device_id, device_code, type, content, create_time, results) \n" +
                                       "values (\n" +
                                       "        "+IdUtil.getSnowflakeNextIdStr() +","+IdUtil.getSnowflakeNextIdStr()+",'xx1','xxx1','1','1',datetime(),1\n" +
                                       "       )");
                               System.out.println("线程2:第"+ tryCount +"次尝试成功!");
                               break;
                           } catch (Exception ec) {

                           }
                        }
                    } else {
                        e.printStackTrace();
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        System.in.read();
        // 关闭线程池
        executorService.shutdown();
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
//    public static void main(String[] args) throws Exception {
//
//
//        CompletableFuture.runAsync(
//                ()-> {
//                    try {
//                        doUpdate();
//                    } catch (Exception e) {
//                        throw new RuntimeException(e);
//                    }
//                }
//        ).exceptionally(e->{
//            e.printStackTrace();
//            return null;
//        });
//        CompletableFuture.runAsync(
//                ()-> {
//                    try {
//                        Thread.sleep(1000);
//                        doUpdate();
//                    } catch (Exception e) {
//                        throw new RuntimeException(e);
//                    }
//                }
//        ).exceptionally(e->{
//            e.printStackTrace();
//            return null;
//        });
//
//        CompletableFuture.runAsync(
//                ()-> {
//                    try {
//                        Thread.sleep(1000);
//                        System.out.println("开始查询");
//                        doQuery();
//                    } catch (Exception e) {
//                        throw new RuntimeException(e);
//                    }
//                }
//        ).exceptionally(e->{
//            e.printStackTrace();
//            return null;
//        });
//
//        System.in.read();
//
//
//    }

    private static void doInsert() throws Exception {
        System.out.println("准备插入。。。");
        Connection con = DriverManager.getConnection("jdbc:sqlite:file:C:\\Users\\Administrator\\Desktop\\license\\fire.sqlite");

        con.setAutoCommit(false);


        con.createStatement().execute("insert into za_sys_message (id, pf_code, device_id, device_code, type, content, create_time, results) \n" +
                "values (\n" +
                "        "+IdUtil.getSnowflakeNextIdStr() +","+IdUtil.getSnowflakeNextIdStr()+",'xx1','xxx1','1','1',datetime(),1\n" +
                "       )");
        System.out.println(" sleep for 3 seconds");
        Thread.sleep(3000);

        con.commit();
        System.out.println("插入完成。。。");
    }

    private static void doQuery() throws Exception {
        Connection con = DriverManager.getConnection("jdbc:sqlite:file:C:\\Users\\Administrator\\Desktop\\license\\fire.sqlite");

        con.setAutoCommit(false);
        List<Entity> entityList = DbUtil.newSqlConnRunner(con).findAll(con, "za_sys_message");

        for (Entity entity : entityList) {
            System.out.println(entity.get("content"));
        }

        con.commit();
    }

    private static void doTruncate() throws Exception {
        Connection con = DriverManager.getConnection("jdbc:sqlite:file:C:\\Users\\Administrator\\Desktop\\license\\fire.sqlite");

        con.setAutoCommit(false);
        con .createStatement().execute("delete from za_sys_message");

        con.commit();
    }

    private static void doUpdate() throws Exception {
        System.out.println("准备更新。。");
        Connection con = DriverManager.getConnection("jdbc:sqlite:file:C:\\Users\\Administrator\\Desktop\\license\\fire.sqlite");

        con.setAutoCommit(false);
        con .createStatement().execute("update za_sys_message set content = 'updated' where id = 1868501193748987904");
        System.out.printf("sleep 3 s");
        Thread.sleep(3000);
        con.commit();
        System.out.println("完成更新");
    }

}