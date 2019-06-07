package com.acongfly.studyjava.disruptor;

import cn.hutool.json.JSONUtil;
import lombok.Data;

import java.util.Date;

/**
 * description: 自定义事件对象 <p>
 * param:  <p>
 * return:  <p>
 * author: shicong yang<p>
 * date: 2019/1/23 <p>
 */
@Data
public class LogEvent {

    private long logId;
    private String content;
    private Date date;

    @Override
    public String toString() {
        return JSONUtil.toJsonStr(this);
    }
}  