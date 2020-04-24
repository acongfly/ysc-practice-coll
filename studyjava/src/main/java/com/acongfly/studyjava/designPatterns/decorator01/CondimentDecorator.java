package com.acongfly.studyjava.designPatterns.decorator01;

/**
 * @program: ysc-practice-coll
 * @description: CondimentDecorator
 * @author: shicong yang
 * @create: 2020-03-27 10:19
 **/
public abstract class CondimentDecorator extends Beverage {
    @Override
    public double cost() {
        return 0;
    }

    public abstract String getDescription();

}
