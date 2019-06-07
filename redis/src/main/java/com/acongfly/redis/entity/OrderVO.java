package com.acongfly.redis.entity;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @program: study
 * @description:
 * @author: shicong yang
 * @create: 2019-04-28 16:58
 **/
@Data
@Builder
public class OrderVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String orderId;
    private long amt;
    private String accountId;
}
