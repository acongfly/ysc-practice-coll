package com.acongfly.studyjava.designPatterns.build;

import java.util.List;

/**
 * program: study<p>
 * description: 部件组装类<p>
 * author: shicong yang<p>
 * createDate: 2019-01-07 15:09<p>
 **/

public class Director {

    private HeroBuilder builder = null;

    public Director(HeroBuilder _builder) {
        builder = _builder;
    }

    public void construct(String playerName, Level level, List<Skill> skills, List<Equipment> equipments) {
        builder.userName(playerName)
                .level(level)
                .equipments(equipments)
                .skills(skills);
    }

}
