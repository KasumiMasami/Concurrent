package com.left.test;

import java.util.concurrent.atomic.AtomicInteger;

public class Test34 {

    public static void main(String[] args) {

        AtomicInteger i = new AtomicInteger(1);

//        // ++i
//        System.out.println(i.incrementAndGet());
//        // i++
//        System.out.println(i.getAndIncrement());
//
//        System.out.println(i.getAndAdd(5));
//        System.out.println(i.addAndGet(5));

        i.updateAndGet(value -> value * 100);

        while (true) {
            int prev = i.get();
            int next = prev * 100;
            if (i.compareAndSet(prev, next)) {
                break;
            }
        }

        System.out.println(i.get());

    }

}
