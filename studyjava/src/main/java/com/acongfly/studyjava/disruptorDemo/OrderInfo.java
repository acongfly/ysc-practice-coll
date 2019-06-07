package com.acongfly.studyjava.disruptorDemo;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

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
