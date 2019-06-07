package com.acongfly.studyjava.designPatterns.template;

/**
 * program: study<p>
 * description: 测试模板模式<p>
 * author: shicong yang<p>
 * createDate: 2019-01-09 16:14<p>
 **/

public class TestTemplate {

    public static void main(String[] args) {
        Game game = new FootballGame();
        game.play();
        System.out.println();
        game = new BasketballGame();
        game.play();
    }
}
