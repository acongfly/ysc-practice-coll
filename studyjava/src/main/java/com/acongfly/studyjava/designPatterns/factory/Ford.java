package com.acongfly.studyjava.designPatterns.factory;

public class Ford implements Car {

    @Override
    public void run() {
        System.out.println("Ford跑起来了");
    }

    @Override
    public void stop() {
        System.out.println("Ford停下来了");
    }

}
