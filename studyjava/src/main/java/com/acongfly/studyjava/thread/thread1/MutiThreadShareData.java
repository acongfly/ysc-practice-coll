package com.acongfly.studyjava.thread.thread1;

/**
 * @author shicongyang
 * @ClassName: MutiThreadShareData
 * @Description: 多种线程数据共享 题目：设计四个线程，其中两个每次对j增加1,另外两个线程对j每次减少一
 * @date 2018年4月22日 下午4:17:54
 */
public class MutiThreadShareData {
    public static void main(String[] args) {
        shareData shareData = new shareData();
        for (int i = 0; i < 2; i++) {
            new Thread(new MyRunnable1(shareData)).start();
            new Thread(new MyRunnable2(shareData)).start();
        }
    }

}

class MyRunnable1 implements Runnable {
    private shareData shareData;

    public MyRunnable1(shareData shareData) {
        this.shareData = shareData;
    }

    @Override
    public void run() {
        shareData.inc();
    }

}

class MyRunnable2 implements Runnable {
    private shareData shareData;

    public MyRunnable2(shareData shareData) {
        this.shareData = shareData;
    }

    @Override
    public void run() {
        shareData.dec();
    }

}

class shareData {
    private int j = 0;

    // 增
    public synchronized void inc() {
        j++;
        System.out.println(Thread.currentThread().getName() + "inc" + j);
    }

    // 减
    public synchronized void dec() {
        j--;
        System.out.println(Thread.currentThread().getName() + "dec" + j);
    }
}
