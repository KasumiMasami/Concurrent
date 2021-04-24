package com.left.test;

import com.left.utils.Sleeper;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

@Slf4j(topic = "c.Test14")
public class Test14 {

    public static void main(String[] args) {
        test3();
    }

    private static void test3() {
        Thread t1 = new Thread(() -> {
            log.debug("park...");
            LockSupport.park();
            log.debug("un-park...");
            // log.debug("打断状态: {}", Thread.currentThread().isInterrupted());
            log.debug("打断状态: {}", Thread.interrupted()); // 获取打断状态后会清除打断状态

            LockSupport.park();
            log.debug("un-park...");
        }, "t1");
        t1.start();

        Sleeper.sleep(1);
        t1.interrupt();
    }
}
