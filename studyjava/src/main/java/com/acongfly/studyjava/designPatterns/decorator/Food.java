package com.acongfly.studyjava.designPatterns.decorator;

/**
 * @author shicongyang
 * @ClassName: Food
 * @Description: 食物（装饰者模式）
 * @date 2018年4月16日 下午9:04:07
 */
public class Food {

    private String foodName;

    public Food() {}

    public Food(String foodName) {
        this.foodName = foodName;
    }

    public String make() {
        return foodName;
    }
}
