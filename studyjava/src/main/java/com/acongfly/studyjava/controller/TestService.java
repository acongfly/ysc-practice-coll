package com.acongfly.studyjava.controller;

// import com.acongfly.study.annotation.CheckValue;
// import com.acongfly.study.annotation.LogSave;

import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import com.acongfly.studyjava.javaStudy.other.FileTrans;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

import lombok.extern.slf4j.Slf4j;

/**
 * @program: ysc-practice-coll
 * @description:
 * @author: shicong yang
 * @create: 2019-07-31 19:51
 **/
@Service
@Slf4j
public class TestService {

    // @LogSave
    // @CheckValue
    public String fileProcess(FileTrans tran) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        System.out.println(tran);
        // byte[] content = tran.getContent();
        // FileUtil.isFile()
        // File file = FileUtil.writeBytes(content, new File("/Users/shicongyang/work/testWrite2.csv"));
        stopWatch.stop();
        System.out.println("total==============:" + stopWatch.getTotalTimeSeconds());
        return "success";
    }

    /**
     * payment to engine
     */
    public static LinkedBlockingQueue<String> paymentToEngineQueue = new LinkedBlockingQueue<>();
    public static ExecutorService paymentToEngine =
        new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors() * 2,
            Runtime.getRuntime().availableProcessors() * 4, 2, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(),
            new ThreadFactoryBuilder().setNameFormat("payment-engine-%d").build(),
            new ThreadPoolExecutor.CallerRunsPolicy());

    // @Scheduled(fixedDelay = 10000)
    public void setQueue() {
        LinkedList<String> list = Lists.newLinkedList();
        for (int i = 0; i < 10000; i++) {
            list.add(i + "");
        }
        for (String s : list) {
            paymentToEngine.execute(() -> {
                try {
                    // System.out.println(Thread.currentThread().getId() + ":" + paymentToEngineQueue.take());
                    log.info("thread============{}", s);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

        // paymentToEngineQueue.addAll(list);
    }

    // @Scheduled(fixedDelay = 10000)
    // public void getQueue() {
    //// while (!paymentToEngineQueue.isEmpty()) {
    //
    //// }
    //
    // }
}
