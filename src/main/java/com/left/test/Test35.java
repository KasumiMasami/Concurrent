package com.left.test;

import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j(topic = "c.Test35")
public class Test35 {

    public static void main(String[] args) {

        DecimalAccount.demo(new DecimalAccountCAS(new BigDecimal("10000")));

    }

}

class DecimalAccountCAS implements DecimalAccount {

    private AtomicReference<BigDecimal> balance;

    DecimalAccountCAS(BigDecimal balance) {
        this.balance = new AtomicReference<>(balance);
    }

    @Override
    public BigDecimal getBalance() {
        return balance.get();
    }

    @Override
    public void withdraw(BigDecimal amount) {
        while (true) {
            BigDecimal prev = balance.get();
            BigDecimal next = prev.subtract(amount);
            if (balance.compareAndSet(prev, next)) {
                break;
            }
        }
    }
}

interface DecimalAccount {

    /**
     * 获取余额
     * @return
     */
    BigDecimal getBalance();

    /**
     * 取款
     * @param amount
     */
    void withdraw(BigDecimal amount);

    static void demo(DecimalAccount account) {

        List<Thread> ts = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            ts.add(new Thread(() -> {
                account.withdraw(BigDecimal.TEN);
            }));
        }
        long start = System.currentTimeMillis();
        ts.forEach(Thread::start);
        ts.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        long end = System.currentTimeMillis();
        System.out.println("Balance: " + account.getBalance() + ", Cost Time: " + (end - start));
    }

}
