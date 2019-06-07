package com.acongfly.studyjava.designPatterns.decorator;

//奶油
public class Cream extends Food {

    private Food basicFood;

    public Cream(Food basicFood) {
        this.basicFood = basicFood;
    }


    public String make() {
        return basicFood.make() + "奶油";
    }

}
