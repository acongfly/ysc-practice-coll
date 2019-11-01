package com.acongfly.studyjava.designPatterns.build;

import java.util.List;

/**
 * program: study
 * <p>
 * description: 英雄的接口实现类
 * <p>
 * author: shicong yang
 * <p>
 * createDate: 2019-01-07 14:59
 * <p>
 **/

public class Traxex implements Hero {

    private String userName; // 玩家id
    private Level level; // 玩家等级
    private List<Skill> skills; // 学习技能点
    private List<Equipment> equipments; // 当前装备

    @Override
    public void setUserName(String name) {
        this.userName = name;
    }

    @Override
    public void setLevel(Level level) {
        this.level = level;
    }

    @Override
    public void setSkill(List<Skill> skills) {
        this.skills = skills;
    }

    @Override
    public void setEquipment(List<Equipment> equipments) {
        this.equipments = equipments;
    }

    public String getUserName() {
        return userName;
    }

    public Level getLevel() {
        return level;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public List<Equipment> getEquipments() {
        return equipments;
    }

    public Traxex(TraxexBuilder _builder) {
        setUserName(_builder.userName);
        setLevel(_builder.level);
        setSkill(_builder.skills);
        setEquipment(_builder.equipments);
    }

    @Override
    public String toString() {
        String level = getLevel().getDesc();

        String skillDesc = "[";
        boolean firstFlag = true;
        for (Skill skill : getSkills()) {
            if (firstFlag) {
                firstFlag = false;
            } else {
                skillDesc += ", ";
            }

            skillDesc += skill.getDesc();
        }
        skillDesc += "]";

        String equipmentDesc = "[";
        firstFlag = true;
        for (Equipment equipment : getEquipments()) {
            if (firstFlag) {
                firstFlag = false;
            } else {
                equipmentDesc += ", ";
            }

            equipmentDesc += equipment.getDesc();
        }
        equipmentDesc += "]";

        return "玩家：" + getUserName() + "\n等级：" + level + "\n学习技能：" + skillDesc + "\n当前装备：" + equipmentDesc;
    }
}
