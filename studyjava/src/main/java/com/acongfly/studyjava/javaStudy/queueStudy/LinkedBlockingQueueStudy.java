package com.acongfly.studyjava.javaStudy.queueStudy;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author shicongyang
 * @Description: LinkedBlockingQueue
 * 基于链表的阻塞队列，同 ArrayBlockingQueue类似，其内部也维持着一个数据缓冲队列
 * （该队列由一个链表构成）， LinkedBlockingQueue之所以可以高效的处理并发数据，是因为其内部实现采用分离锁
 * （读写分离两个锁），从而实现生产者和消费者操作的完全并行运行，它是一个无界队列
 * LinkedBlockingQueue创建时，默认会直接创建一个Integer.MAX_VALUE的数组，当插入少，读取多时，就会造成很大的空间浪费。
 * 而LinkedBlockingQueue实际上实在等需要的时候才会创建一个Node节点。
 * @date 2018/6/6 10:09
 */
public class LinkedBlockingQueueStudy {

    public static void main(String[] args) {
        LinkedBlockingQueue<String> linkedBlockingQueue = new LinkedBlockingQueue<>();
        System.out.println("初始长度：" + linkedBlockingQueue.size());
        for (int i = 0; i < 6; i++) {
            /**
             *  如果可以立即执行而不违反容量限制，则将指定的元素插入此队列，成功时返回true，
             *  如果当前没有空间，则返回IllegalStateException
             */
//            linkedBlockingQueue.add(Integer.toString(i));
            /**
             *  在队列的尾部插入指定的元素，如果没有空间就会一直等待直到有空间
             */
            try {
                linkedBlockingQueue.put(Integer.toString(i));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        System.out.println("增加后：" + linkedBlockingQueue.size());
        System.out.println("增加后值：" + linkedBlockingQueue.toString());
        for (int i = 0; i < 6; i++) {
            /**
             * 检索并删除此队列的头部，如果此队列为空，则返回null。
             */
//            System.out.println("poll单个取值"+linkedBlockingQueue.poll());

            /**
             * 检索但不移除此队列的头部;如果此队列为空，则返回null。
             */
//            System.out.println("peek"+linkedBlockingQueue.peek());


        }
        System.out.println("取出后长度：" + linkedBlockingQueue.size());

    }
}
