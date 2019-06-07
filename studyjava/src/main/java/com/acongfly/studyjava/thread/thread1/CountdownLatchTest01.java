package com.acongfly.studyjava.thread.thread1;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @program: study
 * @description:
 * @author: shicong yang
 * @create: 2019-05-17 15:56
 **/
@Service
public class CountdownLatchTest01 {

    private ExecutorService threadPool = Executors.newCachedThreadPool();
    private CountDownLatch cdOrder = new CountDownLatch(0);        //这个是发号命令

    //    @Scheduled(fixedDelay = 5000)
    public void testCount() throws InterruptedException {
        AtomicInteger size = new AtomicInteger(20);
        if (size.get() == 20) {
            cdOrder = new CountDownLatch(20);
        }
        threadPool.execute(() -> {
            while (size.get() != 0) {
                size.getAndDecrement();
                System.out.println("countdown=============");
                cdOrder.countDown();
            }
        });

//        if (cdOrder.getCount()!=0){
        System.out.println("await============");
        cdOrder.await();
//        }
        if (true) {
            System.out.println("unlock===============");
        }

    }

//    public static void main(String[] args) {
//        CountdownLatchTest01 countdownLatchTest01 = new CountdownLatchTest01();
//        try {
//            countdownLatchTest01.testCount();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
}
