package com.acongfly.studyjava.utils;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * description: 模拟并发工具类（https://mp.weixin.qq.com/s/uDil9FqYVCB0w-TCGokb_A）<p>
 * param:  <p>
 * return:  <p>
 * author: shicong yang <p>
 * date: 2019-08-21 <p>
 */
public class CountDownLatchUtil {

    private CountDownLatch start;
    private CountDownLatch end;
    private int pollSize = 10;

    public CountDownLatchUtil() {
        this(10);
    }

    public CountDownLatchUtil(int pollSize) {
        this.pollSize = pollSize;
        start = new CountDownLatch(1);
        end = new CountDownLatch(pollSize);
    }

    public void latch(MyFunctionalInterface functionalInterface) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(pollSize);
        for (int i = 0; i < pollSize; i++) {
            Runnable run = new Runnable() {
                @Override
                public void run() {
                    try {
                        start.await();
                        functionalInterface.run();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        end.countDown();
                    }
                }
            };
            executorService.submit(run);
        }

        start.countDown();
        end.await();
        executorService.shutdown();
    }

    @FunctionalInterface
    public interface MyFunctionalInterface {
        void run();
    }
}

//
//@RunWith(SpringRunner.class)
// @SpringBootTest
// public class HelloServiceTest {
//
//      @Autowired
//     private HelloService helloService;
//
//      @Test
//     public void testSayHello() throws Exception {
//                long currentTimeMillis = System.currentTimeMillis();
//                //模拟1000个线程并发
//                CountDownLatchUtil countDownLatchUtil = new CountDownLatchUtil(1000);
//                countDownLatchUtil.latch(() -> {
//                        helloService.sayHello(currentTimeMillis);
//                    });
//            }
//
//        }