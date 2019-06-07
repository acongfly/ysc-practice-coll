package com.acongfly.studyjava.disruptor;

import com.lmax.disruptor.RingBuffer;

import java.util.Date;

/**
 * description: 自定义生产者 <p>
 * param:  <p>
 * return:  <p>
 * author: shicong yang<p>
 * date: 2019/1/24 <p>
 */
public class LogEventProducer2 {

    private RingBuffer<LogEvent> ringBuffer;

    public LogEventProducer2(RingBuffer<LogEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    public void onData(long logId, String content, Date date) {
        //RingBuffer类似一个队列，获取下一个空闲的序号  
        long seq = ringBuffer.next();
        LogEvent logEvent = ringBuffer.get(seq);
        logEvent.setLogId(logId);
        logEvent.setContent(content);
        logEvent.setDate(date);
        //发布事件  
        ringBuffer.publish(seq);
    }
}  