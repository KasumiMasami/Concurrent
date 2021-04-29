package com.left.test;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j(topic = "c.Test24")
public class Test24 {

    static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {

        // 创建新的条件变量
        Condition condition1 = lock.newCondition();
        Condition condition2 = lock.newCondition();

        lock.lock();
        // 进入休息室等待
        condition1.await();

        condition1.signal();
        condition1.signalAll();

    }

}
