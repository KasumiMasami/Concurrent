package com.left.test;

import com.left.utils.Sleeper;
import lombok.extern.slf4j.Slf4j;

import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

@Slf4j(topic = "c.Test20")
public class Test20 {

    public static void main(String[] args) {
        // 线程1等待线程2的执行结果
//        GuardedObject guardedObject = new GuardedObject();
//        new Thread(() -> {
//            // 等待结果
//            log.debug("等待结果");
//            String o = (String) guardedObject.get(1000);
//            log.debug("结果: {}", o);
//        }, "t1").start();
//
//        new Thread(() -> {
//            log.debug("执行下载");
//            try {
//                Thread.sleep(5000);
//                guardedObject.complete("10086");
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }, "t2").start();

        for (int i = 0; i < 3; i++) {
            new People().start();
        }
        Sleeper.sleep(1);

        for (Integer id : MailBoxes.getIds()) {
            new Postman(id, "内容" + id).start();
        }
    }

}

@Slf4j(topic = "c.People")
class People extends Thread {
    @Override
    public void run() {
        // 收信
        GuardedObject guardedObject = MailBoxes.createGuardedObject();
        log.debug("开始收信：{}", guardedObject.getId());
        Object mail = guardedObject.get(5000);
        log.debug("收到信 id：{}， 内容：{}", guardedObject.getId(), mail);
    }
}

@Slf4j(topic = "c.Postman")
class Postman extends Thread {

    private int id;
    private String mail;

    public Postman(int id, String mail) {
        this.id = id;
        this.mail = mail;
    }

    @Override
    public void run() {
        GuardedObject guardedObject = MailBoxes.getGuardedObject(id);
        log.debug("送信 id：{}， 内容：{}", id, mail);
        guardedObject.complete(mail);
    }
}

class MailBoxes {

    private static Map<Integer, GuardedObject> boxes = new Hashtable<>();

    private static int id = 1;

    private static synchronized int generateId() {
        return id++;
    }

    public static GuardedObject getGuardedObject(int id) {
        return boxes.remove(id);
    }

    public static GuardedObject createGuardedObject() {
        GuardedObject go = new GuardedObject(generateId());
        boxes.put(go.getId(), go);
        return go;
    }

    public static Set<Integer> getIds() {
        return boxes.keySet();
    }

}

class GuardedObject {

    private int id;

    public GuardedObject(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     *  结果
     */
    private Object response;

    /**
     * 获取结果
     * @param timeout 超时时间
     * @return
     */
    public Object get(long timeout) {
        synchronized (this) {
            // 开始时间
            long begin = System.currentTimeMillis();
            // 经历时间
            long passedTime = 0;

            // 没有结果
            while (response == null) {
                // 等待时间
                long waitTime = timeout - passedTime;
                if (waitTime <= 0) {
                    break;
                }
                try {
                    this.wait(waitTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                passedTime = System.currentTimeMillis() - begin;
            }
            return response;
        }
    }

    /**
     * 产生结果
     * @param response
     */
    public void complete(Object response) {
        synchronized(this) {
            this.response = response;
            this.notifyAll();
        }
    }

}