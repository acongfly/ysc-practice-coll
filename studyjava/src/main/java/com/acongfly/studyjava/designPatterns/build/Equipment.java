package com.acongfly.studyjava.designPatterns.build;

/**
 * description: 装备
 * <p>
 * param:
 * <p>
 * return:
 * <p>
 * author: shicong yang
 * <p>
 * date: 2019/1/7
 * <p>
 */
public enum Equipment {

    Avatar("分身"), Cannon("大炮"), Invisible("隐身斧"), Fly("飞鞋"), BKB("魔法免疫"), TrueEye("真视眼睛");

    private String desc;

    private Equipment(String _desc) {
        desc = _desc;
    }

    public String getDesc() {
        return desc;
    }
}