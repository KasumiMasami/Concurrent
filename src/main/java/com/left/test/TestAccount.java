package com.left.test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class TestAccount {

    public static void main(String[] args) {

        Account account1 = new AccountUnsafe(10000);
        Account.demo(account1);

        Account account2 = new AccountCas(10000);
        Account.demo(account2);

    }

}

class AccountUnsafe implements Account {

    private Integer balance;

    public AccountUnsafe(Integer balance) {
        this.balance = balance;
    }

    @Override
    public Integer getBalance() {
        synchronized (this) {
            return this.balance;
        }
    }

    @Override
    public synchronized void withdraw(Integer amount) {
        synchronized (this) {
            this.balance -= amount;
        }
    }
}

class AccountCas implements Account {

    private AtomicInteger balance;

    public AccountCas(Integer balance) {
        this.balance = new AtomicInteger(balance);
    }

    @Override
    public Integer getBalance() {
        return balance.get();
    }

    @Override
    public void withdraw(Integer amount) {
//        while (true) {
//            // 获取余额的最新值
//            int prev = balance.get();
//            // 要修改的余额
//            int next = prev - amount;
//            // 真正修改
//            if (balance.compareAndSet(prev, next)) {
//                break;
//            }
//        }
        balance.getAndAdd(-1 * amount);
    }
}

interface Account {

    /**
     * 获取余额
     * @return
     */
    Integer getBalance();

    /**
     * 取款
     * @param amount
     */
    void withdraw(Integer amount);

    static void demo(Account account) {

        List<Thread> ts = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            ts.add(new Thread(() -> {
                account.withdraw(10);
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
