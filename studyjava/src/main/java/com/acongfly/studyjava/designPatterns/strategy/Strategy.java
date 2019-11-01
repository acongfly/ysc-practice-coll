package com.acongfly.studyjava.designPatterns.strategy;

/**
 * description: 策略接口
 * <p>
 * param:
 * <p>
 * return:
 * <p>
 * author: shicong yang
 * <p>
 * date: 2018/9/23
 * <p>
 */
public interface Strategy {

    public Double calRecharge(Double charge, RechargeTypeEnum type);
}
