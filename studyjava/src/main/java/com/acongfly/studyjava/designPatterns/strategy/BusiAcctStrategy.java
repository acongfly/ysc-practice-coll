package com.acongfly.studyjava.designPatterns.strategy;

public class BusiAcctStrategy implements Strategy {

    @Override
    public Double calRecharge(Double charge, RechargeTypeEnum type) {
        // TODO Auto-generated method stub
        return charge * 0.90;
    }

}
