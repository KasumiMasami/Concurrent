package com.left.ch03;

import com.left.utils.Sleeper;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "c.TestJoin")
public class TestJoin {
    static int r1 = 0;
    static int r2 = 0;
    public static void main(String[] args) throws InterruptedException {
        // test2();
        test3();
    }

    private static void test2() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            Sleeper.sleep(1);
            r1 = 10;
        }, "t1");
        Thread t2 = new Thread(() -> {
            Sleeper.sleep(2);
            r1 = 20;
        }, "t2");
        t1.start();
        t2.start();
        long start = System.currentTimeMillis();
        log.debug("join begin");
        t1.join();
        log.debug("t1 join end");
        t2.join();
        log.debug("t2 join end");
        long end = System.currentTimeMillis();
        log.debug("r1:{} r2:{} cost:{}", r1, r2, end - start);
    }

    private static void test3() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            Sleeper.sleep(1);
            r1 = 10;
        }, "t1");
        long start = System.currentTimeMillis();
        t1.start();

        log.debug("join begin");
        t1.join(3000);
        long end = System.currentTimeMillis();
        log.debug("r1:{} r2:{} cost:{}", r1, r2, end - start);
    }
}
