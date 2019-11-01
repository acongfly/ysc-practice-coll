package com.acongfly.studyjava.designPatterns.build;

import java.util.ArrayList;
import java.util.List;

/**
 * description: 测试
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
public class BuilderClient {

    public static void main(String[] args) {
        // 技能定义
        List<Skill> skills = getSkills();

        // 装备定义
        List<Equipment> equipments = getEquipments();

        // 构建对象
        TraxexBuilder builder = new TraxexBuilder();
        Director director = new Director(builder);
        director.construct("Naga007", Level.Level_19, skills, equipments);
        Traxex traxex = builder.build();

        System.out.println(traxex);
    }

    // 创建技能列表
    private static List<Skill> getSkills() {
        List<Skill> skills = new ArrayList<>();
        skills.add(Skill.IceArrow);
        skills.add(Skill.SilentMagic);
        skills.add(Skill.TrueshotAura);
        skills.add(Skill.ArcherTalent);

        return skills;
    }

    // 创建装备列表
    private static List<Equipment> getEquipments() {
        List<Equipment> equipments = new ArrayList<>();
        equipments.add(Equipment.Fly);
        equipments.add(Equipment.Avatar);
        equipments.add(Equipment.Cannon);
        equipments.add(Equipment.Invisible);
        equipments.add(Equipment.BKB);
        equipments.add(Equipment.TrueEye);

        return equipments;
    }
}