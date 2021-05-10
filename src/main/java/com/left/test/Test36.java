package com.left.test;

import com.left.utils.Sleeper;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

@Slf4j(topic = "c.Test36")
public class Test36 {

    static AtomicStampedReference<String> ref = new AtomicStampedReference<>("A", 0);
    public static void main(String[] args) {
        log.debug("main start");
        // 获取值
        String prev = ref.getReference();
        int stamp = ref.getStamp();
        log.debug("{}", stamp);
        other();
        Sleeper.sleep(1);
        // 尝试改为 C
        log.debug("{}", stamp);
        log.debug("change A -> C: {}", ref.compareAndSet(prev, "C", stamp, stamp + 1));
    }

    private static void other() {

        new Thread(() -> {
            int stamp = ref.getStamp();
            log.debug("{}", stamp);
            log.debug("change A -> B: {}", ref.compareAndSet(ref.getReference(), "B", stamp, stamp + 1));
        }, "t1").start();
        Sleeper.sleep(0.5);
        new Thread(() -> {
            int stamp = ref.getStamp();
            log.debug("{}", stamp);
            log.debug("change B -> A: {}", ref.compareAndSet(ref.getReference(), "A", stamp, stamp + 1));
        }, "t2").start();

    }

}
