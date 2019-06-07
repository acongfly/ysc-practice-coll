package com.acongfly.studyjava.designPatterns.strategy;

/**
 * description: 策略具体实现 <p>
 * param:  <p>
 * return:  <p>
 * author: shicong yang<p>
 * date: 2018/9/23 <p>
 */
public class EBankStrategy implements Strategy {

    @Override
    public Double calRecharge(Double charge, RechargeTypeEnum type) {
        return charge * 0.85;
    }

}
