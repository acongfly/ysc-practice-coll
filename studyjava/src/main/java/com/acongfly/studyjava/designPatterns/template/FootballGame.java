package com.acongfly.studyjava.designPatterns.template;

/**
 * program: study<p>
 * description: 足球游戏<p>
 * author: shicong yang<p>
 * createDate: 2019-01-09 16:10<p>
 **/

public class FootballGame extends Game {
    @Override
    protected void initialize() {
        System.out.println("football game initialize");
    }

    @Override
    protected void startPlay() {
        System.out.println("football game start play");
    }

    @Override
    protected void endPlay() {
        System.out.println("football game end play");
    }
}
