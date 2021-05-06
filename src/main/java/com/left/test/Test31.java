package com.left.test;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

@Slf4j(topic = "c.Test31")
public class Test31 {

    static Thread t1, t2, t3;

    public static void main(String[] args) {

        ParkUnPark parkUnPark = new ParkUnPark(5);
        t1 = new Thread(() -> {
            parkUnPark.print("a", t2);
        });
        t2 = new Thread(() -> {
            parkUnPark.print("b", t3);
        });
        t3 = new Thread(() -> {
            parkUnPark.print("c", t1);
        });
        t1.start();
        t2.start();
        t3.start();

        LockSupport.unpark(t1);
    }

}

class ParkUnPark {

    private int loopNumber;

    public ParkUnPark(int loopNumber) {
        this.loopNumber = loopNumber;
    }

    public void print(String str, Thread next) {

        for (int i = 0; i < loopNumber; i++) {
            LockSupport.park();
            System.out.print(str);
            LockSupport.unpark(next);
        }

    }

}
