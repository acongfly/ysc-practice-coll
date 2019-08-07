package com.acongfly.yscutils.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class LogInfo implements Serializable {
//    /**
//     * 主键ID
//     */
//    private Integer id;

//    /**
//     * 请求流水号
//     */
//    private String requestNo;

    /**
     * 业务方法(按照接口可以区分)
     */
    private String bizMethod;

//    /**
//     * 商户号
//     */
//    private String merchantId;

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
}