package com.acongfly.studyjava.disruptorDemo;

import java.io.IOException;

import com.acongfly.studyjava.fqueueChange.FQueue;
import com.acongfly.studyjava.fqueueChange.exception.FileFormatException;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @program: study
 * @description: 订单消费者
 * @author: shicong yang
 * @create: 2019-04-26 18:24
 **/
@Slf4j
public class OrderConsumer implements EventHandler<OrderInfo>, WorkHandler<OrderInfo> {

    private FQueue singleMessageFqueue;

    {
        try {
            singleMessageFqueue = new FQueue(1, "/Users/shicongyang/applogs/test", 10 * 1024 * 1024);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (FileFormatException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onEvent(OrderInfo event, long sequence, boolean endOfBatch) throws Exception {
        // log.info("事件消费orderInfo=[{}],sequence=[{}]", event, sequence);
        // Thread.sleep(10000);
        this.onEvent(event);
    }

    @Override
    public void onEvent(OrderInfo event) throws Exception {
        log.info("事件消费orderInfo=[{}],sequence=[{}]", event);
        //// 这里做具体的消费逻辑
        singleMessageFqueue.add(JSONUtil.toJsonStr(event).getBytes());
    }
}
