package com.acongfly.studyjava.designPatterns.strategy02;

import com.google.common.base.Strings;

import lombok.Getter;

/**
 * 付款支付类型枚举 UPI、PAYTM
 *
 * @author xinxiang_jiang@foxmail.com
 * @date 2019/02/25
 */
public enum PaymentPayTypeEnum {

    // 付款支付类型枚举
    UPI("UPI", 0, "upiPaymentServiceImpl"), PAYTM("PAYTM", 1, "payTmPaymentServiceImpl");

    PaymentPayTypeEnum(String name, Integer code, String paymentService) {
        this.name = name;
        this.code = code;
        this.paymentService = paymentService;
    }

    @Getter
    private String name;
    @Getter
    private Integer code;
    @Getter
    private String paymentService;

    public static PaymentPayTypeEnum getValueByCode(Integer code) {
        if (null != code) {
            for (PaymentPayTypeEnum current : values()) {
                if (current.getCode().intValue() == code.intValue()) {
                    return current;
                }
            }
        }
        return null;
    }

    public static PaymentPayTypeEnum getValueByName(String name) {
        if (!Strings.isNullOrEmpty(name)) {
            for (PaymentPayTypeEnum current : values()) {
                if (current.getName().equals(name)) {
                    return current;
                }
            }
        }
        return null;
    }

    /**
     * 当前币种是否包括在枚举类中
     *
     * @param payType
     *            付款方式
     * @return 返回是否
     */
    public static Boolean isContainPayType(String payType) {
        for (PaymentPayTypeEnum paymentPayTypeEnum : values()) {
            if (paymentPayTypeEnum.getName().equals(payType)) {
                return true;
            }
        }
        return false;
    }
}
