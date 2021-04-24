package com.left.test;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j(topic = "c.Test9")
public class Test9 {

    public static void main(String[] args) throws InterruptedException {

       Runnable task1 = () -> {
           int count = 0;
           for (;;) {
               log.debug("------->1 {}", count++);
           }
       };

        Runnable task2 = () -> {
            int count = 0;
            for (;;) {
                // Thread.yield();
                log.debug("         ------->2 {}", count++);
            }
        };

        Thread t1 = new Thread(task1, "t1");
        Thread t2 = new Thread(task2, "t2");
        t1.setPriority(Thread.MAX_PRIORITY);
        t2.setPriority(Thread.MIN_PRIORITY);

        t1.start();
        t2.start();

    }

}
