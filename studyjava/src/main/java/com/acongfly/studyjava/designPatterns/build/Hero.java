package com.acongfly.studyjava.designPatterns.build;

import java.util.List;

/**
 * description: 英雄的抽象接口
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
public interface Hero {

    void setUserName(String name);

    void setLevel(Level level);

    void setSkill(List<Skill> skills);

    void setEquipment(List<Equipment> equipments);

}