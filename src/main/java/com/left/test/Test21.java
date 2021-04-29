package com.left.test;

import com.left.utils.Sleeper;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;

@Slf4j(topic = "c.Test21")
public class Test21 {

    public static void main(String[] args) {

        MessageQueue messageQueue = new MessageQueue(2);

        for (int i = 0; i < 3; i++) {
            int id = i;
            new Thread(() -> {
                messageQueue.put(new Message(id, "值" + id));
            }, "生产者" + i).start();
        }

        new Thread(() -> {
            while (true) {
                Sleeper.sleep(1);
                messageQueue.take();
            }
        }, "消费者").start();
    }

}

/**
 * 消息队列类，JVM 线程之间通信
 */
@Slf4j(topic = "c.MessageQueue")
class MessageQueue {

    /**
     * 消息的队列集合
     */
    private LinkedList<Message> list = new LinkedList<>();

    /**
     * 队列容量
     */
    private int capcity;

    public MessageQueue(int capcity) {
        this.capcity = capcity;
    }

    /**
     * 获取消息
      * @return
     */
    public Message take() {
        // 检查对象是否为空
        synchronized(list) {
            while (list.isEmpty()) {
                try {
                    log.debug("队列为空，消费者线程等待");
                    list.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            // 从队列头部获取消息
            Message message = list.removeFirst();
            log.debug("已消费消息 {}", message);
            list.notifyAll();
            return message;
        }
    }

    /**
     * 存入消息
     * @param message
     */
    public void put(Message message) {
        synchronized (list) {
            while (list.size() == capcity) {
                try {
                    log.debug("队列满了，生产者线程等待");
                    list.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            list.addLast(message);
            log.debug("已生产消息 {}", message);
            list.notifyAll();
        }
    }

}

final class Message {

    private int id;
    private Object value;

    public Message(int id, Object value) {
        this.id = id;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", value=" + value +
                '}';
    }

}
