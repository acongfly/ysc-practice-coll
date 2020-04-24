package com.acongfly.studyjava.designPatterns.decorator01;

/**
 * @program: ysc-practice-coll
 * @description: 装饰者模式学习：https://blog.csdn.net/lemon_tree12138/article/details/45870027
 * @author: shicong yang
 * @create: 2020-03-27 09:50
 **/
public abstract class Beverage {

    public String mDescription = "UnKown Beverage";
    private int size = 0;

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getmDescription() {
        return mDescription;
    }

    public abstract double cost();
}
