package com.left.test;

import lombok.extern.slf4j.Slf4j;
import org.openjdk.jol.info.ClassLayout;

@Slf4j(topic = "c.TestBiased")
public class TestBiased {

    public static void main(String[] args) throws InterruptedException {
        Dog d = new Dog();
        log.debug(ClassLayout.parseInstance(d).toPrintable(d));
        Thread.sleep(4000);

//        synchronized (d) {
//            log.debug(ClassLayout.parseInstance(d).toPrintable(d));
//        }

        log.debug(ClassLayout.parseInstance(d).toPrintable(d));
    }

}

class Dog {

}
