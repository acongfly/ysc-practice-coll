package com.acongfly.studyjava.disruptor;

import com.lmax.disruptor.EventHandler;

/**
 * description: 自定义消费者  <p>
 * param:  <p>
 * return:  <p>
 * author: shicong yang<p>
 * date: 2019/1/24 <p>
 */
public class LogEventConsumer implements EventHandler<LogEvent> {
    @Override
    public void onEvent(LogEvent logEvent, long l, boolean b) throws Exception {
        System.out.println("消费者1-seq:" + l + ",bool:" + b + ",logEvent:" + logEvent.toString());
    }
}  