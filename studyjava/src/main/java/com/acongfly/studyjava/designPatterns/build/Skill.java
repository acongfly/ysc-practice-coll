package com.acongfly.studyjava.designPatterns.build;

/**
 * description: 技能 <p>
 * param:  <p>
 * return:  <p>
 * author: shicong yang<p>
 * date: 2019/1/7 <p>
 */
public enum Skill {

    IceArrow("霜冻之箭(R)"), SilentMagic("沉默魔法(E)"), TrueshotAura("强击光环(T)"), ArcherTalent("射手天赋(M)");

    private String desc;

    private Skill(String _desc) {
        desc = _desc;
    }

    public String getDesc() {
        return desc;
    }
}