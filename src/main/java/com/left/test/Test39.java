package com.left.test;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@Slf4j(topic = "c.Test39")
public class Test39 {

    public static void main(String[] args) {
        demo(
                () -> new int[10],
                (array) -> array.length,
                (array, index) -> array[index]++,
                array -> log.debug(Arrays.toString(array))
        );

        demo(
                () -> new AtomicIntegerArray(10),
                (array) -> array.length(),
                (array, index) -> array.getAndIncrement(index),
                (array) -> log.debug(array.toString())
        );
    }

    /**
     *
     * @param arraySupplier 提供数组，可以是线程不安全数组或线程安全数组
     * @param lengthFun 获取数组长度的方法
     * @param putConsumer 自增方法，回传 array, index
     * @param printConsumer 打印数组的方法
     * @param <T>
     */
    private static <T> void demo (Supplier<T> arraySupplier,
                                  Function<T, Integer> lengthFun,
                                  BiConsumer<T, Integer> putConsumer,
                                  Consumer<T> printConsumer)  {
        List<Thread> ts = new ArrayList<>();
        T array = arraySupplier.get();
        int length = lengthFun.apply(array);
        for (int i = 0; i < length; i++) {
            ts.add(new Thread(() -> {
                for (int j = 0; j < 10000; j++) {
                    putConsumer.accept(array, j % length);
                }
            }));
        }
        ts.forEach(t -> t.start());
        ts.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        printConsumer.accept(array);
    }

}
