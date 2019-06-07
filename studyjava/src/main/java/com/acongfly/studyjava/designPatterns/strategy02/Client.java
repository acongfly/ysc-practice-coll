package com.acongfly.studyjava.designPatterns.strategy02;

/**
 * @program: study
 * @description: test
 * @author: shicong yang
 * @create: 2019-06-01 16:37
 **/
public class Client {

    public static void main(String[] args) {
        AbstractAccInfoBaseSupport upi = AccInfoFactory.getInstance().creator("UPI");
        BaseAccInfoJson baseAccInfoJson = new BaseAccInfoJson();
        baseAccInfoJson.setPayeeAccount("12345678");
        baseAccInfoJson.setPayTmAppliedToNewUsers("Y");
        String s = upi.buildAccInfo(baseAccInfoJson);
        System.out.println(s);
        BaseAccInfoJson baseAccInfoJson1 = upi.readAccInfo(s);
        System.out.println(baseAccInfoJson1);
        System.out.println(baseAccInfoJson.getPayeeAccount());

    }
}
