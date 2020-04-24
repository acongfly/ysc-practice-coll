package com.acongfly.studyjava.designPatterns.decorator01;

/**
 * @program: ysc-practice-coll
 * @description: DarkRoast
 * @author: shicong yang
 * @create: 2020-03-27 10:23
 **/
public class DarkRoast extends Beverage {

    private double mCost = 0.99;

    public DarkRoast() {
        mDescription = "Dark Roast Coffee";
    }

    @Override
    public double cost() {
        return mCost;
    }
}
