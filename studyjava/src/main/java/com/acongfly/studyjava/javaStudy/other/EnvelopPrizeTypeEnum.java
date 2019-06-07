package com.acongfly.studyjava.javaStudy.other;

/**
 * description: 红包分配策略，1 random 2 fix 3 random with range <p>
 * param:  <p>
 * return:  <p>
 * author: shicong yang<p>
 * date: 2018/11/21 <p>
 */
public enum EnvelopPrizeTypeEnum {

    RANDOM(1, "随机分配"),
    FIX(2, "等额分配"),
    RANDOM_WITH_RANGE(3, "带范围分配"),
    ;

    private final int value;
    private final String description;

    EnvelopPrizeTypeEnum(int value, String description) {
        this.value = value;
        this.description = description;

    }

    public int getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    /**
     * 枚举转换
     */
    public static EnvelopPrizeTypeEnum parseOf(int value) {
        for (EnvelopPrizeTypeEnum item : values()) {
            if (item.getValue() == value) {
                return item;
            }
        }
        return null;
    }

}
