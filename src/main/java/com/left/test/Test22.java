package com.left.test;

import com.left.utils.Sleeper;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j(topic = "c.Test22")
public class Test22 {

    private static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {
        // 可重入
//        lock.lock();
//        try {
//            log.debug("enter main");
//            m1();
//        } finally {
//            lock.unlock();
//        }
        // 可打断
//        Thread t1 = new Thread(() -> {
//            try {
//                log.debug("尝试获取锁");
//                // 如果没有竞争那么此方法会获取 lock 对象锁
//                // 如果有竞争就进入阻塞队列，可以被其他线程用 interrupt 方法打断
//                lock.lockInterruptibly();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//                log.debug("没有获得锁，返回");
//                return;
//            }
//
//            try {
//                log.debug("获取到锁");
//            } finally {
//                lock.unlock();
//            }
//        }, "t1");
//
//        try {
//            lock.lock();
//            t1.start();
//
//            Sleeper.sleep(1);
//            log.debug("打断t1");
//            t1.interrupt();
//        } finally {
//            lock.unlock();
//        }
        // 锁超时
        Thread t1 = new Thread(() -> {
            log.debug("尝试获得锁");
            try {
                if (!lock.tryLock(2, TimeUnit.SECONDS)) {
                    log.debug("获取不到锁");
                    return;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                log.debug("获取不到锁");
                return;
            }
            try {
                log.debug("获取到锁");
            } finally {
                lock.unlock();
            }
        }, "t1");

        lock.lock();
        log.debug("获得到锁");
        t1.start();
        Sleeper.sleep(1);
        lock.unlock();
        log.debug("释放了锁");

    }

    public static void m1() {
        lock.lock();
        try {
            log.debug("enter m1");
            m2();
        } finally {
            lock.unlock();
        }
    }

    public static void m2() {
        lock.lock();
        try {
            log.debug("enter m2");
        } finally {
            lock.unlock();
        }
    }


}
