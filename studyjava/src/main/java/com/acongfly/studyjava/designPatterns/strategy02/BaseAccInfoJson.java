package com.acongfly.studyjava.designPatterns.strategy02;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: fintech-payment
 * @description: 账户信息json基础类
 * @author: shicong yang
 * @create: 2019-06-01 15:12
 **/
@Data
public class BaseAccInfoJson implements Serializable {
    private static final long serialVersionUID = 7045614133335364178L;
    /**
     * payTm账户信息
     */
    private String payeeAccount;

    /**
     * payTm是否开立新账户，默认是N。Y/N
     */
    private String payTmAppliedToNewUsers;

}
