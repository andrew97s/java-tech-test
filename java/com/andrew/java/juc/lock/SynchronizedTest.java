package com.andrew.java.juc.lock;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.openjdk.jol.info.ClassLayout;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author tongwenjin
 * @since 2022/7/7
 */

@Slf4j
@SuppressWarnings("all")
public class SynchronizedTest {

    public static void main(String[] args) {

        /*
        * 可参考 ： https://tech.meituan.com/2018/11/15/java-lock.html、
        *
        * 一 . 实现背景
        * JOL java object layout java内存布局
        * java内存中的对象一般由 对象头（markword + class pointer） + 实例数据（instance data） + padding
        * (将对象长度保持为8字节 、 数组对象为4字节 的整数倍 ，以提升操作效率)
        *
        * java 对象都包含对应的对象头 ， 对象头由mark word (标记字)& klass pointer（类型指针）构成
        * mark word 存储了对象的 hashcode & 垃圾回收年龄 （15次进入老年代） & GC标识 & 锁信息
        *
        * 二、 实现原理
        * 1. synchronized 同步代码块 实现为一个 锁对象（Object） 都会唯一对应一个monitor对象（CPP实现）
        * 编译的字节码文件中可以看出一个 同步代码块开始为 monitorenter （尝试获取monitor对象锁） 、
        * 同步代码块结束为 monitorexit （释放monitor锁） 获取不到则 对应的线程被阻塞进入wait状态
        *
        * 2. 同步方法 及锁对象为class对象或者实例对象的monitor 被synchronized修饰的方法 ，编译的字节码 会有ACC_SYNCHRONIZD 修饰符
        * monitor底层都是依赖操作系统的mutex lock 实现 ，
        * 故需要线程 从jvm的用户态切换到操作系统的内核态 ，而此过程很耗性能 故synchronized 属于重量级锁
        *
        *
        * 三、 jvm1.6 后的锁优化
        * jdk synchronized 的优化
        *  锁升级机制 单项、不可逆（无锁-> 偏向锁 -> 轻量级锁 -> 重量级锁）
        *  1. 锁分类
        *       1.1 无锁 ： 同一资源可以被多个线程同时访问、修改 ， 但是只有一个线程能够成功 ， CAS 即是无锁的实现
        *       1.2 偏向锁 ：针对单线程竞争锁资源的场景，该锁为同一线程持有（对象头保存了线程ID）
        *           后续线程获取该锁 不需要去竞争该锁 从而省略锁竞争代理的资源开销 进而提升效率
        *       1.3 轻量级锁 ：两个线程竞争锁资源 ， 非持有锁对象的线程优先通过自旋 等待锁释放
        *       1.4 重量级锁 ：超过两个线程同时竞争锁资源，会导致没有竞争到锁资源的线程挂起 ，
        *           等待持有锁对象的线程释放锁后调度 操作系统唤醒 等待的线程 （会涉及线程由 用户态转为内核态的过程 较为耗费资源）
        *
        *  2.1. 适应性自旋 （当线程没有竞争到锁时 不会立即挂起 ， 而是尝试循环多次获取锁对象；根据获取成功率 jdk会动态调整自旋次数）
        *  2.2. 锁消除 （当jdk监测到锁内不存在变量逃逸时 ， 会把对应的锁消除掉）
        *  2.3. 锁粗化 （当多个同步代码操作时 ， 会频繁的做加锁 ， 上锁操作 ， jvm会将其优化为一个加锁动作 从而节约多次加锁导致的性能浪费）
        * */

        Lock object = new Lock();
        System.out.println(ClassLayout.parseInstance(object).toPrintable());
        synchronized (object) {
            System.out.println("Sychronized");
        }

        Lock nonLockObject = new Lock();
        System.out.println(ClassLayout.parseInstance(nonLockObject).toPrintable());
    }

    static class Lock {
        int intField;

        private void sayHi() {}
    }

    public synchronized void sayHi(){}

    public static synchronized void sayHello(){}

    @Test
    @SneakyThrows
    public void testVariableFlee() {
        ReentrantLock reentrantLock = new ReentrantLock();
        Object lock = new Object();
        for (int i = 0; i < 10; i++) {
            final int intt = i;
            new Thread(()->{
                lockMethod(intt);
            }).start();
        }

        System.in.read();
    }

    private synchronized static void lockMethod(int i) {
        System.out.println(Thread.currentThread().getName() + " says hi !" + i);
    }
}
