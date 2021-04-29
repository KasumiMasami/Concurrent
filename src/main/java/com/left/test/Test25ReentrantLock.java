package com.left.test;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j(topic = "c.Test25ReentrantLock")
public class Test25ReentrantLock {

    static final ReentrantLock lock = new ReentrantLock();
    static Condition t2Wait = lock.newCondition();
    // 表示t2是否运行过
    static boolean t2runned = false;

    public static void main(String[] args) {

        Thread t1 = new Thread(() -> {
            try {
                lock.lock();
                while (!t2runned) {
                    try {
                        t2Wait.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log.debug("1");
            } finally {
                lock.unlock();
            }
        }, "t1");

        Thread t2 = new Thread(() -> {
            try {
                lock.lock();
                log.debug("2");
                t2runned = true;
                t2Wait.signal();
            } finally {
                lock.unlock();
            }
        }, "t2");

        t1.start();
        t2.start();

    }

}
