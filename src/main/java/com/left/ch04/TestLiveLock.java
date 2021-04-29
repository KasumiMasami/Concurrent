package com.left.ch04;

import com.left.utils.Sleeper;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "c.TestLiveLock")
public class TestLiveLock {

    static volatile int count = 10;
    static final Object lock = new Object();

    public static void main(String[] args) {

        new Thread(() -> {
            while (count > 0) {
                Sleeper.sleep(0.2);
                count--;
                log.debug("count: {}", count);
            }
        }, "t1").start();

        new Thread(() -> {
            while (count < 20) {
                Sleeper.sleep(0.2);
                count++;
                log.debug("count: {}", count);
            }
        }, "t2").start();

    }

}
