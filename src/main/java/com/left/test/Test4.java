package com.left.test;

import com.left.Constants;
import com.left.utils.FileReader;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "c.Test4")
public class Test4 {

    public static void main(String[] args) {
        Thread t1 = new Thread("t1") {
            @Override
            public void run() {
                FileReader.read(Constants.MP4_FULL_PATH);
                log.debug("running...");
            }
        };

        // t1.run();
        // start 才会启动线程
        t1.start();
        log.debug("do other things...");
    }
}
