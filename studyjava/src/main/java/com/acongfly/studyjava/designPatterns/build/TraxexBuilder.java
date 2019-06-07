package com.acongfly.studyjava.designPatterns.build;

import java.util.List;

/**
 * program: study<p>
 * description: 具体的创建者实现创建接口<p>
 * author: shicong yang<p>
 * createDate: 2019-01-07 14:49<p>
 **/

public class TraxexBuilder implements HeroBuilder {

    private Traxex traxex;

    public String userName; // 玩家id
    public Level level; // 玩家等级
    public List<Skill> skills; // 学习技能点
    public List<Equipment> equipments; // 当前装备

    @Override
    public HeroBuilder userName(String _name) {
        userName = _name;
        return this;
    }

    @Override
    public HeroBuilder level(Level _level) {
        level = _level;
        return this;
    }

    @Override
    public HeroBuilder skills(List<Skill> _skills) {
        skills = _skills;
        return this;
    }

    @Override
    public HeroBuilder equipments(List<Equipment> _equipments) {
        equipments = _equipments;
        return this;
    }

    public Traxex build() {
        if (traxex == null) {
            traxex = new Traxex(this);
        }
        return traxex;
    }
}
