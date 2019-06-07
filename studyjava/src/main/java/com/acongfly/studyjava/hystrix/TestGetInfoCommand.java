package com.acongfly.studyjava.hystrix;

import com.netflix.hystrix.HystrixCommand;
import rx.Observable;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @program: study
 * @description: 测试线程隔离
 * @see: http://www.10tiao.com/html/164/201704/2652898372/1.html
 * @author: shicong yang
 * @create: 2019-04-12 20:48
 **/
public class TestGetInfoCommand {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        HystrixCommand command = new GetInfoProcessCommand("1234567890");
        //同步
//        String result = command.execute();
//        System.out.println("===result="+result);
//        或者返回Future从而实现异步调用。
//        Future<String> future = command.queue();
//        System.out.println(future.get());
//        或者配合RxJava实现响应式编程
        Observable<String> observe = command.observe();
        observe.asObservable().subscribe((result) -> {
            System.out.println(result);
        });


    }
}
