package com.left.ch02;

import com.left.Constants;
import com.left.utils.FileReader;
import lombok.extern.slf4j.Slf4j;

/**
 * 异步不等待
 */
@Slf4j(topic = "c.Async")
public class Async {

    public static void main(String[] args) {
        new Thread(() -> FileReader.read(Constants.MP4_FULL_PATH)).start();
        log.debug("do other things ...");
    }

}
