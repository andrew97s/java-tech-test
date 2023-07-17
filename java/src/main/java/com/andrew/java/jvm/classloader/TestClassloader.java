package com.andrew.java.jvm.classloader;

import com.andrew.java.juc.lock.CountDownLatchTest;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import sun.misc.Launcher;

import java.lang.reflect.Method;

/**
 * 1. 类加载器分类
 *  1.1 BootstrapClassLoader 非java语言实现 ， 用来加载rt.jar包
 *  1.2 ExtensionClassLoader 扩展类加载器 ， 用来加载jre/lib/ext 下的jar包
 *  1.3 ApplicationClassLoader 应用类加载器 ， 加载classpath 指定的jar包
 *
 * 2. 类加载机制
 *  2.1 双亲委派机制 ， 任何一个类加载亲求都会经过 appClsLoader -> ExtClsLoader -> BtpClsLoader 这一路径 向上级loader委派处理
 *      上级loader处理不了才会经由自己处理
 *  2.2 可见性原则 ， 父类loader加载的class 对子类可见 ， 而子类loader所加载的类对父类loader不可见
 *  2.3 唯一性原则 ， 一个class只允许被一个loader所加载一次
 *
 *
 *
 * @author tongwenjin
 * @since 2022/7/18
 */

@Slf4j
public class TestClassloader {

    @SneakyThrows
    public static void main(String[] args) {

        // Launcher.AppClassLoader
        Class<?> appClass = Class.forName("com.andrew.java.juc.lock.CountDownLatchTest");

        /*
        * {@link sun.misc.Launcher.AppClassLoader}
        * */
        Class<?> extensionClass = Class.forName("sun.text.resources.cldr.aa.FormatData_aa");

        // BoostrapClassLoader 非java语言实现 故此处为空
        Class<?> boostrapClass = Class.forName("com.oracle.net.Sdp");


        System.out.println(appClass.getClassLoader());
        System.out.println(extensionClass.getClassLoader());
        System.out.println(boostrapClass.getClassLoader());


        Launcher launcher = new Launcher();

        System.out.println(launcher.getClassLoader());
        System.out.println(launcher.getClass().getClassLoader());

        // classPath 属性
        String classPath = System.getProperty("java.class.path");
        String[] classPaths = classPath.split(";");
        for (String path : classPaths) {
//            System.out.println(path);
        }

        ClassLoader appLoader = Thread.currentThread().getContextClassLoader();

        System.out.println(appLoader.getParent());
    }


    @Test
    @SneakyThrows
    public void test1() {

        CustomClassloader cl = new CustomClassloader(Thread.currentThread().getContextClassLoader().getParent());

        Class<?> aClass = cl.loadClass("com.andrew.java.jvm.classloader.TestClassloader.TestClassA");

        Object instance = aClass.newInstance();

        Method sayHi = aClass.getDeclaredMethod("sayHi");
        sayHi.setAccessible(true);

        sayHi.invoke(instance);

        System.out.println(aClass.getClassLoader());
//        TestClassA.sayHi();
    }


    public static class TestClassA {
        public static void sayHi() {
            System.out.printf("hi there ! loaded by : %s%n", TestClassA.class.getClassLoader());
            TestClassB.sayHi();
        }
    }

    public static class TestClassB {
        public static void sayHi() {
            System.out.printf("hi there ! loaded by : %s%n", TestClassB.class.getClassLoader());
        }
    }
}
