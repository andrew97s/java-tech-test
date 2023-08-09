package com.andrew.java.collection;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author tongwenjin
 * @since 2022/7/7
 */

@SuppressWarnings("all")
@Slf4j
public class CopyOnWriteTest {

    public static void main(String[] args) {

        // 线程安全的list实现类 内部所有的方法都通过ReentrantLock 加锁实现

        // 每次修改都会导致 内部重新赋值产生一个新的数组对象

        // 适用于多频繁遍历的并发场景 iterator 方法(永远不会抛出 currentModificationException)

        List list = new CopyOnWriteArrayList();

        list.iterator();

    }

}
