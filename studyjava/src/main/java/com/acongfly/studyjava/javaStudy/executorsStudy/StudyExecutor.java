package com.acongfly.studyjava.javaStudy.executorsStudy;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程池学习 Created by hc on 2018/7/9.
 */
public class StudyExecutor {

    public void executorServiceStudy() {
        LinkedBlockingQueue<Study> lkbq = new LinkedBlockingQueue<>();
        Study study = new Study();
        AtomicInteger atomicInteger = new AtomicInteger();
        ExecutorService threadPool = Executors.newSingleThreadExecutor(); // 单线程
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    int andAdd = atomicInteger.getAndAdd(1);
                    study.setId(andAdd + "id");
                    study.setNotityName(andAdd + "name");
                    study.setNotityTime(andAdd + "Time");
                    try {
                        lkbq.put(study);
                        // Study poll = lkbq.poll();
                        // System.out.println(poll);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        // new Thread(new Runnable() {
        // @Override
        // public void run() {
        // while (true) {
        // int andAdd = atomicInteger.getAndAdd(1);
        // study.setId(andAdd + "id");
        // study.setNotityName(andAdd + "name");
        // study.setNotityTime(andAdd + "Time");
        // try {
        // lkbq.put(study);
        //// Study poll = lkbq.poll();
        //// System.out.println(poll);
        // } catch (InterruptedException e) {
        // e.printStackTrace();
        // }
        // }
        // }
        // }).start();

        // new Thread(new Runnable() {
        // @Override
        // public void run() {
        // while(true){
        // try {
        // Study take = lkbq.take();
        // System.out.println(take);
        // } catch (InterruptedException e) {
        // e.printStackTrace();
        // }
        // }
        // }
        // }).start();
        for (int i = 0; i < 3; i++) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            System.out.println(Thread.currentThread().getName());
                            Study take = lkbq.take();
                            System.out.println(take);
                            System.out.println(lkbq.size());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                }
            });
        }

    }

    public static void main(String[] args) {
        StudyExecutor studyExecutor = new StudyExecutor();
        studyExecutor.executorServiceStudy();
    }
}
