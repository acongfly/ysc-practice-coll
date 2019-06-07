package com.acongfly.studyjava.designPatterns.template;

/**
 * program: study<p>
 * description: 模板模式---举游戏例子<p>
 * author: shicong yang<p>
 * createDate: 2019-01-09 16:07<p>
 **/

public abstract class Game {

    protected abstract void initialize();

    protected abstract void startPlay();

    protected abstract void endPlay();


    //模板 按照顺序启动游戏操作
    public final void play() {
        //初始化游戏
        this.initialize();
        //开始游戏
        this.startPlay();
        //结束游戏
        this.endPlay();
    }
}
