package com.acongfly.studyjava.designPatterns.decorator;

//面包
public class Bread extends Food {

    private Food basicFood;

    public Bread(Food basicFood) {
        this.basicFood = basicFood;
    }

    public String make() {
        return basicFood.make() + "面包";
    }
}
