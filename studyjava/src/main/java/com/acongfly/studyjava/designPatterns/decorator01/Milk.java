package com.acongfly.studyjava.designPatterns.decorator01;

/**
 * @program: ysc-practice-coll
 * @description:
 * @author: shicong yang
 * @create: 2020-03-27 11:53
 **/
public class Milk extends CondimentDecorator {

    private Beverage mBeverage = null;
    private double mCost = 0.10;

    public Milk(Beverage beverage) {
        mBeverage = beverage;
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public double cost() {
        return mCost + mBeverage.cost();
    }
}
