package com.acongfly.studyjava.disruptor;

import java.util.Date;

import com.lmax.disruptor.EventTranslatorVararg;
import com.lmax.disruptor.RingBuffer;

/**
 * program: study
 * <p>
 * description: 使用translator方式到事件生产者发布事件,通常使用该方法
 * <p>
 * author: shicong yang
 * <p>
 * createDate: 2019-01-24 10:01
 * <p>
 **/

public class LogEventProducerWithTranslator {

    private EventTranslatorVararg eventTranslatorVararg = new EventTranslatorVararg<LogEvent>() {
        @Override
        public void translateTo(LogEvent logEvent, long l, Object... objects) {
            logEvent.setLogId((Long)objects[0]);
            logEvent.setContent((String)objects[1]);
            logEvent.setDate((Date)objects[2]);
        }
    };

    private RingBuffer<LogEvent> ringBuffer;

    public LogEventProducerWithTranslator(RingBuffer<LogEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    public void onData(long logId, String content, Date date) {
        ringBuffer.publishEvent(eventTranslatorVararg, logId, content, date);
    }
}