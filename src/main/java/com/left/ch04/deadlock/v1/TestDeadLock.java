package com.left.ch04.deadlock.v1;

import com.left.utils.Sleeper;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantLock;

public class TestDeadLock {

    public static void main(String[] args) {
        Chopstick chopstick1 = new Chopstick("1");
        Chopstick chopstick2 = new Chopstick("2");
        Chopstick chopstick3 = new Chopstick("3");
        Chopstick chopstick4 = new Chopstick("4");
        Chopstick chopstick5 = new Chopstick("5");

        new Person("p1", chopstick1, chopstick2).start();
        new Person("p2", chopstick2, chopstick3).start();
        new Person("p3", chopstick3, chopstick4).start();
        new Person("p4", chopstick4, chopstick5).start();
        new Person("p5", chopstick5, chopstick1).start();
    }

}

@Slf4j(topic = "c.People")
class Person extends Thread {

    Chopstick left;
    Chopstick right;

    public Person(String name, Chopstick left, Chopstick right) {
        super(name);
        this.left = left;
        this.right = right;
    }

    @Override
    public void run() {
        while (true) {
//            synchronized (left) { // 尝试获得左手筷子
//
//                synchronized (right) { // 尝试获得右手筷子
//                    eat();
//                }
//            }
            if (left.tryLock()) {
                try {
                    if (right.tryLock()) {
                        try {
                            eat();
                        } finally {
                            right.unlock();
                        }
                    }
                } finally {
                    left.unlock();
                }
            }
        }
    }

    private void eat() {
        log.debug("eating...");
        Sleeper.sleep(1);
    }

}

class Chopstick extends ReentrantLock {

    String name;

    public Chopstick(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "chopstick{" +
                "name='" + name + '\'' +
                '}';
    }

}