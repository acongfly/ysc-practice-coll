package com.acongfly.studyjava.thread.thread1;

/**
 * @author shicongyang
 * @ClassName: TraditionalTread
 * @Description: 传统的线程方式
 * @date 2018年4月21日 下午4:46:01
 */
public class TraditionalTread {


    //创建线程的方式
    public static void main(String[] args) {
        //创建线程1.（方法一）
        Thread thread = new Thread() {
            @Override
            public void run() {
                //定义一个循环
                while (true) {
                    //较少cpu压力
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("ThreadName1=" + Thread.currentThread().getName());
                }
            }
        };
        //运行线程
        thread.start();

        //创建线程2.（方式二）更加体现面向对象的过程以及思维
        Thread thread2 = new Thread(new Runnable() {

            @Override
            public void run() {
                //定义一个循环
                while (true) {
                    //较少cpu压力
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("ThreadName2=" + Thread.currentThread().getName());
                }
            }
        });

        thread2.start();


    }

}
