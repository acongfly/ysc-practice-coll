package com.acongfly.studyjava.thread.thread1;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author shicongyang
 * @ClassName: TraditionalTimer
 * @Description: 传统的定时器
 * @date 2018年4月21日 下午5:08:25
 */
public class TraditionalTimer {

    public static void main(String[] args) {
        //定时器调度一个任务(十秒以后炸，以后每三秒炸一个)
        new Timer().schedule(new TimerTask() {

            @Override
            public void run() {
                System.out.println("boom!!!");
            }
        }, 10000, 3000);


        //写内部类，可以让炸弹每隔2秒就炸一个

        class MyTimeTask extends TimerTask {

            @Override
            public void run() {
                System.out.println("------boom!!!");
                new Timer().schedule(new MyTimeTask(), 2000);
            }

        }
        new Timer().schedule(new MyTimeTask(), 2000);

        while (true) {
            System.out.println(new Date().getSeconds());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
