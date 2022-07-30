package com.andrew.java.jvm.classloader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * 自定义类加载器
 *
 * @author tongwenjin
 * @since 2022/7/28
 */
public class CustomClassloader extends ClassLoader {

    private Map<String, String> classPathMap = new HashMap<>();

    public CustomClassloader(ClassLoader classLoader) {
        jdkClassLoader = classLoader;
        classPathMap.put(
                "com.andrew.java.jvm.classloader.TestClassloader.TestClassA",
                "D:\\project\\idea\\test\\java\\target\\classes\\com\\andrew\\java\\jvm\\classloader\\TestClassloader$TestClassA.class"
        );
        classPathMap.put(
                "com.andrew.java.jvm.classloader.TestClassloader.TestClassB",
                "D:\\project\\idea\\test\\java\\target\\classes\\com\\andrew\\java\\jvm\\classloader\\TestClassloader$TestClassB.class"
        );
    }

    // The findClass method is overridden
//    @Override
//    public Class<?> findClass(String name) throws ClassNotFoundException {
//        String classPath = classPathMap.get(name);
//        File file = new File(classPath);
//        if (!file.exists()) {
//            throw new ClassNotFoundException();
//        }
//        byte[] classBytes = getClassData(file);
//        if (classBytes == null || classBytes.length == 0) {
//            throw new ClassNotFoundException();
//        }
//        return defineClass(classBytes, 0, classBytes.length);
//    }

    private byte[] getClassData(File file) {
        try (
                InputStream ins = new FileInputStream(file);
                ByteArrayOutputStream baos = new ByteArrayOutputStream()
        ) {
            byte[] buffer = new byte[4096];
            int bytesNumRead = 0;
            while ((bytesNumRead = ins.read(buffer)) != -1) {
                baos.write(buffer, 0, bytesNumRead);
            }
            return baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new byte[]{};
    }

    private ClassLoader jdkClassLoader;

    @Override
    protected Class<? > loadClass(String name, boolean resolve) throws ClassNotFoundException {
        Class result = null;
        try {
            // Here we need to use the class loader of JDK to load the classes included in the java.lang package.
            result = jdkClassLoader.loadClass(name);
        } catch (Exception e) {
            // Ignore
        }
        if (result != null) {
            return result;
        }
        String classPath = classPathMap.get(name);
        File file = new File(classPath);
        if (! file.exists()) {
            throw new ClassNotFoundException();
        }

        byte[] classBytes = getClassData(file);
        if (classBytes == null || classBytes.length == 0) {
            throw new ClassNotFoundException();
        }
        return defineClass(classBytes, 0, classBytes.length);
    }
}
