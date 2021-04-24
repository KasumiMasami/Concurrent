package com.left.test;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "c.Test7")
public class Test7 {

    public static void main(String[] args) throws InterruptedException {

        Thread t1 = new Thread("t1") {
            @Override
            public void run() {
                try {
                    log.debug("enter sleep...");
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    log.debug("wake up...");
                    e.printStackTrace();
                }
            }
        };
         t1.start();

        Thread.sleep(1000);

        log.debug("interrupt...");
        t1.interrupt();

    }

}
