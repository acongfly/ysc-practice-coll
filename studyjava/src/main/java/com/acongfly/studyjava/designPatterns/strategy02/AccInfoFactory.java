package com.acongfly.studyjava.designPatterns.strategy02;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: fintech-payment
 * @description: 账户信息处理工厂
 * @author: shicong yang
 * @create: 2019-06-01 15:19
 **/
public class AccInfoFactory {

    public static AccInfoFactory accInfoFactory = new AccInfoFactory();

    private AccInfoFactory() {
    }

    private static Map strategyMap = new HashMap<>();

    static {
        strategyMap.put(PaymentPayTypeEnum.UPI.getName(), new UpiAccinfoJson());
        strategyMap.put(PaymentPayTypeEnum.PAYTM.getName(), new PayTmAccInfoJson());
    }

    public AbstractAccInfoBaseSupport creator(String paymentType) {
        return (AbstractAccInfoBaseSupport) strategyMap.get(paymentType);
    }

    public static AccInfoFactory getInstance() {
        return accInfoFactory;
    }
}
