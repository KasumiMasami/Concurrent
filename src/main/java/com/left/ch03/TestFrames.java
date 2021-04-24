package com.left.ch03;

public class TestFrames {

    public static void main(String[] args) {
        Thread t1 = new Thread("t1") {
            @Override
            public void run() {
                method1(20);
            }
        };
        t1.start();

        method1(10);

    }

    private static void method1(int x) {
        int y = x + 1;
        Object m = method2();
        System.out.println(m);
    }

    private static Object method2() {
        return new Object();
    }
}
