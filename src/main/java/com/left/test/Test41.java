package com.left.test;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Slf4j(topic = "c.Test41")
public class Test41 {

    public static void main(String[] args) {
        int time = 5;
        for (int i = 0; i < time; i++) {
            demo(
                    () -> new AtomicLong(0),
                    (adder) -> adder.getAndIncrement()
            );
        }

        for (int i = 0; i < time; i++) {
            demo(
                    () -> new LongAdder(),
                    adder -> adder.increment()
            );
        }

    }

    private static <T> void demo(Supplier<T> adderSupplier, Consumer<T> action) {

        T adder = adderSupplier.get();
        List<Thread> ts = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            ts.add(new Thread(() -> {
                for (int j = 0; j < 500000; j++) {
                    action.accept(adder);
                }
            }));
        }
        long start = System.currentTimeMillis();
        ts.forEach(t -> t.start());
        ts.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        long end = System.currentTimeMillis();
        log.debug("cost: " + (end - start));
    }

}
