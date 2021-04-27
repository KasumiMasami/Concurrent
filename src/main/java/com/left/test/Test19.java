package com.left.test;

import com.left.utils.Sleeper;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "c.Test19")
public class Test19 {
    static final Object lock = new Object();

    public static void main(String[] args) {

        new Thread(() -> {
            synchronized(lock) {
                log.debug("获得锁");
                try {
                    // Sleeper.sleep(20);
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "t1").start();

        Sleeper.sleep(1);
        synchronized(lock) {
            log.debug("获得锁");
        }

    }

}
