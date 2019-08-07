package com.acongfly.study.model;


import java.io.Serializable;
import java.util.Date;

public class LogInfo implements Serializable {

    /**
     * 业务方法(按照接口可以区分)
     */
    private String bizMethod;

    /**
     * 请求报文
     */
    private String reqMsg;

    /**
     * 响应报文
     */
    private String respMsg;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 方法开始时间
     */
    private Long methodStartTime;

    /**
     * 方法结束时间
     */
    private Long methodEndTime;

    private static final long serialVersionUID = 1L;

    public String getBizMethod() {
        return bizMethod;
    }

    public void setBizMethod(String bizMethod) {
        this.bizMethod = bizMethod;
    }

    public String getReqMsg() {
        return reqMsg;
    }

    public void setReqMsg(String reqMsg) {
        this.reqMsg = reqMsg;
    }

    public String getRespMsg() {
        return respMsg;
    }

    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getMethodStartTime() {
        return methodStartTime;
    }

    public void setMethodStartTime(Long methodStartTime) {
        this.methodStartTime = methodStartTime;
    }

    public Long getMethodEndTime() {
        return methodEndTime;
    }

    public void setMethodEndTime(Long methodEndTime) {
        this.methodEndTime = methodEndTime;
    }
}