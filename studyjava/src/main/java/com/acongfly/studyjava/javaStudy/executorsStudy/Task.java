package com.acongfly.studyjava.javaStudy.executorsStudy;

/**
 * @author shicongyang
 * @Description: Task任务相关字段
 * @date 2018/6/12 10:46
 */
public class Task implements Runnable {

    private int id;

    private String threadName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getThreadName() {
        return threadName;
    }

    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }

    public Task() {

    }

    public Task(int id, String threadName) {
        this.id = id;
        this.threadName = threadName;
    }

    @Override
    public void run() {
        System.out.println("任务id=" + this.id);
        System.out.println(Thread.currentThread().getName());
        //模拟业务处理
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Override
    public String toString() {
        //此处的toString方法进行返回id,针对与处理失败或者拒绝的任务记录ID
        return Integer.toString(this.id);
    }
}
