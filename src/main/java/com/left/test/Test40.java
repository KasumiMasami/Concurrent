package com.left.test;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

@Slf4j(topic = "c.Test40")
public class Test40 {

    public static void main(String[] args) {
        Student student = new Student();

        AtomicReferenceFieldUpdater updater = AtomicReferenceFieldUpdater.newUpdater(Student.class, String.class, "name");

        updater.compareAndSet(student, null, "张三");

        log.debug(student.toString());
    }

}

class Student {
    volatile String name;

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                '}';
    }
}
