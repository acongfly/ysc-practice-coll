package com.acongfly.studyjava.disruptorDemo;

import java.io.Serializable;

import lombok.Data;

/**
 * @program: study
 * @description: 订单信息
 * @author: shicong yang
 * @create: 2019-04-26 18:07
 **/
@Data
public class OrderInfo implements Serializable {

    private String orderId;
    private String requestNo;
    private Long amt;
    private int orderType;

}
