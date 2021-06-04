package com.left.ch08;

import com.left.utils.Sleeper;
import lombok.extern.slf4j.Slf4j;
import org.openjdk.jmh.annotations.Benchmark;

import java.util.concurrent.locks.ReentrantReadWriteLock;

@Slf4j(topic = "c.TestReadWriteLock")
public class TestReadWriteLock {

    public static void main(String[] args) {

        DataContainer dataContainer = new DataContainer();

        new Thread(() -> {
            dataContainer.write();
        }, "t1").start();

        Sleeper.sleep(0.1);

        new Thread(() -> {
            dataContainer.read();
        }, "t2").start();

    }

}

@Slf4j(topic = "c.DataContainer")
class DataContainer {
    private Object data;
    private ReentrantReadWriteLock rw = new ReentrantReadWriteLock();
    private ReentrantReadWriteLock.ReadLock r = rw.readLock();
    private ReentrantReadWriteLock.WriteLock w =  rw.writeLock();

    public Object read() {
        log.debug("获取读锁...");
        r.lock();
        try {
            log.debug("read");
            Sleeper.sleep(1);
            return data;
        } finally {
            log.debug("释放读锁...");
            r.unlock();
        }
    }

    public void write() {
        log.debug("获取写锁...");
        w.lock();
        try {
            log.debug("write");
            Sleeper.sleep(1);
        } finally {
            log.debug("释放写锁...");
            w.unlock();
        }
    }

}
