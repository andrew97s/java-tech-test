package com.andrew.java.core;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.crypto.digest.MD5;
import lombok.extern.slf4j.Slf4j;

import java.io.Closeable;
import java.util.Date;

/**
 * @author tongwenjin
 * @since 2022/7/29
 */

@Slf4j
public class GrammarCandyTest {


    public static void main(String[] args) {

        // try resources 语法糖 ， jdk编译后 会对代码块内实现autoCloseable接口的对象 调用其close方法
        try (
            TestAutoCloseBle closeBle = new TestAutoCloseBle();
        ) {
            System.out.println(1/0);
            closeBle.sayHi();

        }
    }

    static class TestAutoCloseBle implements AutoCloseable {

        public void sayHi() {
            log.info("hi there !!!");
        }

        @Override
        public void close()  {

            log.info("closed !!!");
        }
    }
}
