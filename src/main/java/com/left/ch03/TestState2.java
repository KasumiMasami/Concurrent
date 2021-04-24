package com.left.ch03;

import com.left.Constants;
import com.left.utils.FileReader;

public class TestState2 {

    public static void main(String[] args) {
        new Thread(() -> {
            FileReader.read(Constants.MP4_FULL_PATH);
            FileReader.read(Constants.MP4_FULL_PATH);
            FileReader.read(Constants.MP4_FULL_PATH);
        }, "t1").start();

        System.out.println("ok");
    }
}
