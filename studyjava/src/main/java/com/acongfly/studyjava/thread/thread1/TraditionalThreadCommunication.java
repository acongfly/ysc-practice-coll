package com.acongfly.studyjava.thread.thread1;

/**
 * @author shicongyang
 * @ClassName: TraditionalThreadCommunication
 * @Description: 线程同步通信
 * @date 2018年4月21日 下午9:04:44
 */
public class TraditionalThreadCommunication {

    //解决题目：子线程循环十次，接着主线程循环10次，如此循环50次
    public static void main(String[] args) {
        final Business business = new Business();
        new Thread(new Runnable() {

            @Override
            public void run() {
                for (int i = 0; i < 50; i++) {
                    business.sub();
                }
            }
        }).start();


        for (int i = 0; i < 50; i++) {
            business.mains();
        }
    }

}

/**
 * @author shicongyang
 * @ClassName: Business
 * @Description: 业务匿名内部类
 * @date 2018年4月21日 下午11:05:08
 */
class Business {

    private boolean shouldCut = true;

    public synchronized void sub() {
        while (!shouldCut) {            //如果不为true就进行等待
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for (int j = 0; j < 10; j++) {
            System.out.println("子线程循环次数" + j);
        }
        shouldCut = false;        //置标记shouldCut为false
        this.notify();        //通知下一等待
    }

    public synchronized void mains() {
        while (shouldCut) {                    //while 和if都可以实现效果，但是使用while可以防止假唤醒。
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for (int j = 0; j < 10; j++) {
            System.out.println("主线程循环次数" + j);
        }
        shouldCut = true;
        this.notify();
    }

}
