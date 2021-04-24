package com.left.test;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "c.Test5")
public class Test5 {

    public static void main(String[] args) {

        Thread t1 = new Thread("t1") {
            @Override
            public void run() {
                log.debug("running...");
            }
        };

        System.out.println(t1.getState()); // NEW
        // 不可多次调用 start
        // t1.start();
        t1.start();
        System.out.println(t1.getState()); // RUNNABLE
    }

}
