package com.left.test;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "c.Test1")
public class Test1 {

    public static void main(String[] args) {
        t1();
    }

    public static void t1() {
        Thread t = new Thread("t1") {
            @Override
            public void run() {
                log.debug("running");
            }
        };
        t.start();

        log.debug("running");
    }

    public static void t2() {
        // Runnable r = () -> log.debug("running");
        Runnable r = new Runnable() {
            @Override
            public void run() {
                log.debug("running");
            }
        };
        Thread t = new Thread(r, "t2");
        t.start();

        log.debug("running");
    }

}
