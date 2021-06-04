package com.left.ch08;

import com.left.utils.Sleeper;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j(topic = "c.TestThreadPoolExecutors")
public class TestThreadPoolExecutors {

    public static void main(String[] args) throws InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(2, new ThreadFactory() {
            private AtomicInteger t = new AtomicInteger(1);
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "mypool_t" + t.getAndIncrement());
            }
        });

        pool.execute(() -> {
            log.debug("1");
            Sleeper.sleep(1);
        });

        pool.execute(() -> {
            log.debug("2");
            Sleeper.sleep(1);
        });

        pool.execute(() -> {
            log.debug("3");
            Sleeper.sleep(1);
        });

        pool.shutdown();
        pool.awaitTermination(3, TimeUnit.SECONDS);
        log.debug("other...");
    }

}
