package com.acongfly.studyjava.javaStudy.other;

import lombok.Data;

/**
 * @program: ysc-practice-coll
 * @description:
 * @author: shicong yang
 * @create: 2019-11-07 17:25
 **/
@Data
public class TestSeam {

    /**
     * mobile : 62-882345678 verifiedTime : 2001-07-04T12:08:56+05:30 externalUid : TIXxxxxxUID reqTime :
     * 2001-07-04T12:08:56+05:30 reqMsgId : 1234567asdfasdf1123fda
     */

    private String mobile;
    private String verifiedTime;
    private String externalUid;
    private String reqTime;
    private String reqMsgId;

    private String riskData;

    // @Data
    // public static class RiskData {
    // private String fuzzyDeviceId;
    // private String terminalType;
    // private String riskFlag;
    // private String realIp;
    // }

}
