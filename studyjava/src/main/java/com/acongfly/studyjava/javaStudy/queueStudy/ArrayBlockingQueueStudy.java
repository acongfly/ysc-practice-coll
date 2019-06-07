package com.acongfly.studyjava.javaStudy.queueStudy;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * @author shicongyang
 * @Description: ArrayBlockingQueue
 * 基于数组的阻塞队列实现，在 ArrayBlockingQueue内维护了一个定长数组，以便缓存队列中的数据对象
 * 其内部没实现读写分离，ArrayBlockingQueue则读写共享一个锁，意味着生产和消费不能完全并行，长度是需要定义的，
 * 可以指定先进先出或者先进后出，也叫有界队列，在很多场合非常适合适用
 * @date 2018/6/6 9:52
 */
public class ArrayBlockingQueueStudy {

    public static void main(String[] args) {

        ArrayBlockingQueue<String> arrayBlockingQueue = new ArrayBlockingQueue<>(5);
        System.out.println("增加值之前" + arrayBlockingQueue.size());
        for (int i = 0; i < 5; i++) {
            //add
            //设置的初始值为5,当使用add方法的时候超过5,则 报异常Exception in thread "main" java.lang.IllegalStateException: Queue full
            /**
             * add()
             在不超出队列长度的情况下插入元素，可以立即执行，成功返回true，如果队列满了就抛出异常
             * 其底层实现的是offer方法，不会阻塞
             * public boolean add(E e) {
             *    if (offer(e))
             return true;
             else
             throw new IllegalStateException("Queue full");
             }
             */
            boolean add = arrayBlockingQueue.add(i + "");
            System.out.println(add);
            /**
             * offer（）方法：
             * 在不超出队列长度的情况下插入元素的时候则可以立即在队列的尾部插入指定元素
             * 成功时返回true，如果此队列已满，则返回false。
             * 不会阻塞
             *
             */
//            boolean offer = arrayBlockingQueue.offer(i + "");
//            System.out.println(offer);
            /**
             * put()方法：
             * 插入元素的时候，如果队列满了就进行等待，直到队列可用
             */
//            try {
//                arrayBlockingQueue.put(i+"");
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }
        System.out.println("增加值之后" + arrayBlockingQueue.size());
        System.out.println(arrayBlockingQueue.toString());

        System.out.println("取值开始：");
        for (int i = 0; i < 5; i++) {
            /**
             *  remove（）方法取值：
             *  底层是用到了poll()方法，检索并且删除返回队列头的元素
             *  与poll()方法不同的是，元素没有是进行抛异常NoSuchElementException
             *public E remove() {
             E x = poll();
             if (x != null)
             return x;
             else
             throw new NoSuchElementException();
             }
             */
//            System.out.println("取出的值为："+arrayBlockingQueue.remove());

            /**
             * poll()
             * 检索并且删除返回队列头的元素,有就返回没有就返回null
             */
//            System.out.println("取出的值为："+arrayBlockingQueue.poll());
            /**
             * take()
             * 检索并且删除返回队列头的元素,如果元素没有会一直等待，有就返回
             */
//            try {
//                System.out.println("取出的值为："+arrayBlockingQueue.take());
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            /**
             * peek()
             * 检索但不移除此队列的头部;如果此队列为空，则返回null。
             * 返回头部元素
             */
            System.out.println("取出的值为：" + arrayBlockingQueue.peek());

        }


    }

}
