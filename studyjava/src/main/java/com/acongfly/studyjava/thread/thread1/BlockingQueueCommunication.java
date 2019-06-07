package com.acongfly.studyjava.thread.thread1;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @author shicongyang
 * @ClassName: BlockingQueueCommunication
 * @Description: 阻塞队列之间的通信, 模拟两个队列进行轮流交换通信
 * @date 2018年5月1日 下午6:28:15
 */
public class BlockingQueueCommunication {

    public static void main(String[] args) {
        businesses businesses = new businesses();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    businesses.sub(i);
                }
            }
        }).start();
        for (int i = 0; i < 10; i++) {
            businesses.main(i);
        }
    }
}

class businesses {
    BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(1);
    BlockingQueue<Integer> queue2 = new ArrayBlockingQueue<>(1);

    //匿名内部方法，在每次实例化new的时候都是会执行一遍，并且是首先执行
    {
        System.out.println("队列放数据开始");
        try {
            queue2.put(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void sub(int i) {
        try {
            queue.put(1);
            for (int j = 0; j < 10; j++) {
                System.out.println("Sub 线程j=" + j + ",i=" + i);
            }
            queue2.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void main(int i) {
        try {
            queue2.put(1);
            for (int j = 0; j < 10; j++) {
                System.out.println("main 线程j=" + j + ",i=" + i);
            }
            queue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}