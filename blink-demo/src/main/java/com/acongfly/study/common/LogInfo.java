package com.acongfly.study.common;

import java.io.Serializable;

import lombok.Data;

@Data
public class LogInfo implements Serializable {

    /**
     * 业务方法(按照接口可以区分)
     */
    private String bizMethod;

    /**
     * 请求报文(JSON)
     */
    private String reqMsg;

    /**
     * 响应报文(JSON)
     */
    private String respMsg;

    /**
     * 创建时间
     */
    private Long createTime = System.currentTimeMillis();

    /**
     * 方法开始时间
     */
    private Long methodStartTime;

    /**
     * 方法结束时间
     */
    private Long methodEndTime;

    private static final long serialVersionUID = 1L;

}