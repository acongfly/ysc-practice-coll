package com.acongfly.studyjava.designPatterns.template;

/**
 * program: study
 * <p>
 * description: 篮球游戏
 * <p>
 * author: shicong yang
 * <p>
 * createDate: 2019-01-09 16:13
 * <p>
 **/

public class BasketballGame extends Game {
    @Override
    protected void initialize() {
        System.out.println("Basketball game initialize");
    }

    @Override
    protected void startPlay() {
        System.out.println("Basketball game start play");
    }

    @Override
    protected void endPlay() {
        System.out.println("Basketball game end play");
    }
}
