package com.acongfly.studyjava.utils;

/**
 * description: 账户状态
 * <p>
 * param:
 * <p>
 * return:
 * <p>
 * author: shicong yang
 * <p>
 * date: 2018/7/23
 * <p>
 */
public enum AccountStatusEnum {
    OPEN_ACC(0, "开户"), CAN_USE(1, "可用(审核后)"), FREEZE(2, "冻结"), DEL_ACC(3, "销户"),;

    private final int value;
    private final String description;

    AccountStatusEnum(int value, String description) {
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
    public static AccountStatusEnum parseOf(int value) {
        for (AccountStatusEnum item : values()) {
            if (item.getValue() == value) {
                return item;
            }
        }
        return null;
    }
}
