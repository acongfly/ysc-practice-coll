package com.acongfly.studyjava.disruptor;

import com.lmax.disruptor.EventFactory;

/**
 * description: 事件生成工厂,用来初始化预分配事件对象,即根据RingBuffer大小创建的实体对象  <p>
 * param:  <p>
 * return:  <p>
 * author: shicong yang<p>
 * date: 2019/1/23 <p>
 */
public class LogEventFactory implements EventFactory<LogEvent> {
    @Override
    public LogEvent newInstance() {
        System.out.println("新建LogEvent数据.....");
        return new LogEvent();
    }
}  