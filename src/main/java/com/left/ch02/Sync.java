package com.left.ch02;

import com.left.Constants;
import com.left.utils.FileReader;
import lombok.extern.slf4j.Slf4j;

/**
 * 同步等待
 */
@Slf4j(topic = "c.Sync")
public class Sync {

    public static void main(String[] args) {
        FileReader.read(Constants.MP4_FULL_PATH);
        log.debug("do other things ...");
    }

}
