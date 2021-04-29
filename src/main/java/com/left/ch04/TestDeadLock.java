package com.left.ch04;

import com.left.utils.Sleeper;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "c.TestDeadLock")
public class TestDeadLock {

    public static void main(String[] args) {
        Object A = new Object();
        Object B = new Object();

        Thread t1 = new Thread(() -> {
            synchronized (A) {
                log.debug("lock A");
                Sleeper.sleep(1);
                synchronized (B) {
                    log.debug("lock B");
                    log.debug("操作...");
                }
            }
        }, "t1");

        Thread t2 = new Thread(() -> {
            synchronized (B) {
                log.debug("lock B");
                Sleeper.sleep(1);
                synchronized (A) {
                    log.debug("lock A");
                    log.debug("操作...");
                }
            }
        }, "t2");

        t1.start();
        t2.start();
    }

}
