package com.acongfly.studyjava.designPatterns.strategy;

/**
 * description: 策略模式的需要优化的代码 <p>
 * 首先，按照if...else if语句来实现打折商品的例子，代码如下：
 * param:  <p>
 * return:  <p>
 * author: shicong yang<p>
 * date: 2018/9/23 <p>
 */
public class Example {


    public Double calRecharge(Double charge, RechargeTypeEnum type) {

        if (type.equals(RechargeTypeEnum.E_BANK)) {
            return charge * 0.85;
        } else if (type.equals(RechargeTypeEnum.BUSI_ACCOUNTS)) {
            return charge * 0.90;
        } else if (type.equals(RechargeTypeEnum.MOBILE)) {
            return charge;
        } else if (type.equals(RechargeTypeEnum.CARD_RECHARGE)) {
            return charge + charge * 0.01;
        } else {
            return null;
        }

    }

}
