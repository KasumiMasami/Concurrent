package com.left.test;

import com.left.utils.Sleeper;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "c.Test32")
public class Test32 {
    // 易变
    static volatile Boolean run = true;
    // 锁对象
    final static Object lock = new Object();

    public static void main(String[] args) {
        // t1();
        t2();
    }

    public static void t1() {
        Thread t = new Thread(() -> {
            while (run) {
                // ...
            }
        });
        t.start();

        Sleeper.sleep(1);
        log.debug("停止 t");
        run = false;
    }

    public static void t2() {
        Thread t = new Thread(() -> {
            while (run) {
                // ...
                synchronized (lock) {
                    if (!run) {
                        break;
                    }
                }
            }
        });
        t.start();

        Sleeper.sleep(1);
        log.debug("停止 t");
        synchronized (lock) {
            run = false;
        }
    }

}
