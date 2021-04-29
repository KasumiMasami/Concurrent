package com.left.ch04;

import com.left.utils.Sleeper;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "c.TestMultiLock")
public class TestMultiLock {

    public static void main(String[] args) {
        BigRoom bigRoom = new BigRoom();
        new Thread(() -> {
            bigRoom.sleep();
        }, "t1").start();

        new Thread(() -> {
            bigRoom.study();
        }, "t2").start();
    }

}

@Slf4j(topic = "c.BigRoom")
class BigRoom {

    private final Object sleepRoom = new Object();

    private final Object studyRoom = new Object();

    public void sleep() {
        synchronized(sleepRoom) {
            log.debug("sleeping 2小时");
            Sleeper.sleep(2);
        }
    }

    public void study() {
        synchronized(studyRoom) {
            log.debug("studying 1小时");
            Sleeper.sleep(1);
        }
    }

}
