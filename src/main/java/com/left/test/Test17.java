package com.left.test;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "c.Test17")
public class Test17 {

    public static void main(String[] args) throws InterruptedException {
        Room room = new Room();

        Thread t1 = new Thread(() -> {
           for (int i = 0; i < 5000; i++) {
               room.increament();
           }
        }, "t1");

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
                room.decreament();
            }
        }, "t2");

        t1.start();
        t2.start();
        t1.join();
        t2.join();
        log.debug("counter: {}", room.getCounter());
    }

}

class Room {
    static int counter = 0;

    public synchronized void increament() {
        counter++;
    }

    public synchronized void decreament() {
        counter--;
    }

    public synchronized int getCounter() {
        return counter;
    }

}
