package com.left.test;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "c.Test13")
public class Test13 {

    public static void main(String[] args) throws InterruptedException {
        TwoPhaseTermination twoPhaseTermination = new TwoPhaseTermination();
        twoPhaseTermination.start();
        twoPhaseTermination.start();
        twoPhaseTermination.start();

        Thread.sleep(3500);
        log.debug("停止监控");
        twoPhaseTermination.stop();
    }

}

@Slf4j(topic = "c.TwoPhaseTermination")
class TwoPhaseTermination {
    // 监控线程
    private Thread monitorThread;
    // 停止标记
    private volatile boolean stop = false;
    // 是否执行过start方法
    private volatile boolean starting = false;

    /**
     * 启动监控线程
     */
    public void start() {
        if (!starting) {
            synchronized (this) {
                if (starting) {
                    return;
                }
                starting = true;
            }
            monitorThread = new Thread(() -> {
                while (true) {
                    // 是否被打断
                    if (stop) {
                        log.debug("料理后事");
                        break;
                    }
                    try {
                        Thread.sleep(1000);
                        log.debug("执行监控记录");
                    } catch (InterruptedException e) {

                    }
                }
            }, "monitor");


            monitorThread.start();
        }
    }

    /**
     * 停止监控线程
     */
    public void stop() {
        stop = true;
        monitorThread.interrupt();
    }

}
