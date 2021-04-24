package com.left.ch03;

import lombok.extern.slf4j.Slf4j;

/**
 * 多个线程并发交替执行
 */
@Slf4j(topic = "c.TestMultiThread")
public class TestMultiThread {

    public static void main(String[] args) {
        new Thread(() -> {
            while (true) {
                log.debug("running");
            }
        }, "t1").start();

        new Thread(() -> {
            while (true) {
                log.debug("running");
            }
        }, "t2").start();
    }

}
