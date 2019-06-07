package com.acongfly.studyjava.designPatterns.build;

import java.util.List;

/**
 * program: study<p>
 * description: hero build构建类 抽象出,参考:http://blog.csdn.net/lemon_tree12138/article/details/50246499<p>
 * author: shicong yang<p>
 * createDate: 2019-01-07 14:35<p>
 **/

public interface HeroBuilder {

    HeroBuilder userName(String _name);

    HeroBuilder level(Level _level);

    HeroBuilder skills(List<Skill> _skills);

    HeroBuilder equipments(List<Equipment> _equipments);

}
