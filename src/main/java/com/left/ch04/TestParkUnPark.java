package com.left.ch04;

import com.left.utils.Sleeper;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

@Slf4j(topic = "c.TestParkUnPark")
public class TestParkUnPark {

    public static void main(String[] args) {

        Thread t1 = new Thread(() -> {
            log.debug("start...");
            Sleeper.sleep(1);
            log.debug("park...");
            LockSupport.park();
            log.debug("resume...");
        }, "t1");
        t1.start();

        Sleeper.sleep(2);
        log.debug("unPark...");
        LockSupport.unpark(t1);

    }

}
