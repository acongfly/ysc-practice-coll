package com.acongfly.studyjava.javaStudy.queueStudy.delayQueueStudy;

import lombok.Builder;
import lombok.Data;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @program: study
 * @description: 消息告知(message 实体类)
 * @author: shicong yang
 * @create: 2019-04-30 15:02
 **/
@Data
@Builder
public class Message implements Delayed {
    private int id;
    private String body;  //消息内容
    private long excuteTime;//执行时间

    public Message(int id, String body, long delayTime) {
        this.id = id;
        this.body = body;
        this.excuteTime = TimeUnit.NANOSECONDS.convert(delayTime, TimeUnit.MILLISECONDS) + System.nanoTime();
    }


    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(this.excuteTime - System.nanoTime(), TimeUnit.NANOSECONDS);
    }

    @Override
    public int compareTo(Delayed delayed) {
        return (int) (this.getDelay(TimeUnit.MILLISECONDS) - delayed.getDelay(TimeUnit.MILLISECONDS));
    }
}
