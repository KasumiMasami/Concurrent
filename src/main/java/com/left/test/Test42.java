package com.left.test;

import com.left.utils.UnsafeAccesssor;
import lombok.extern.slf4j.Slf4j;
import sun.misc.Unsafe;

@Slf4j(topic = "c.Test42")
public class Test42 {

    public static void main(String[] args) {

        Account.demo(new MyAtomicInteger(10000));

    }

}

class MyAtomicInteger implements Account {

    private volatile int value;
    private static final Unsafe UNSAFE;
    private static final long valueOffset;

    static {
        UNSAFE = UnsafeAccesssor.getUnsafe();
        try {
            valueOffset = UNSAFE.objectFieldOffset(MyAtomicInteger.class.getDeclaredField("value"));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public int getValue() {
        return value;
    }

    public void decreament(int amount) {

        while (true) {
            int prev = this.value;
            int next = prev - amount;
            if (UNSAFE.compareAndSwapInt(this, valueOffset, prev, next)) {
                break;
            }
        }

    }

    public MyAtomicInteger(int value) {
        this.value = value;
    }

    @Override
    public Integer getBalance() {
        return getValue();
    }

    @Override
    public void withdraw(Integer amount) {
        decreament(amount);
    }
}
