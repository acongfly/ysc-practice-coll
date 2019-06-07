package com.acongfly.studyjava.spring;/**
 * Created by hc on 2018/12/9.
 */

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.Future;

/**
 * program: study<p>
 * description: 异步方法操作者,
 * 注意 @Async所修饰的函数不要定义为static类型，这样异步调用不会生效
 * <p>
 * <p>事务：：方法A，使用了@Async/@Transactional来标注，但是无法产生事务控制的目的。 方法B，使用了@Async来标注， B中调用了C、D，C/D分别使用@Transactional做了标注，则可实现事务控制的目的。
 * author: shicong yang<p>
 * createDate: 2018-12-09 20:33<p>
 **/
@Component
public class AsyncStudy {

    @Async
    public Future<String> sayHello1() throws InterruptedException {
        int thinking = 2;
        Thread.sleep(thinking * 1000);//网络连接中 。。。消息发送中。。。
        System.out.println("我爱你啊!");
        return new AsyncResult<String>("发送消息用了" + thinking + "秒");
    }

}
