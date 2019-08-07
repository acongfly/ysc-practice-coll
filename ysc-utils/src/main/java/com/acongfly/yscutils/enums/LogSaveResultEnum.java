package com.acongfly.yscutils.enums;

import lombok.Getter;

/**
 * @program: ysc-practice-coll
 * @description: 错误枚举
 * @author: shicong yang
 * @create: 2019-07-07 17:30
 **/
public enum LogSaveResultEnum {

    SUCCESS("200", "success"),
    FAIL("500", "fail"),
    ;


    @Getter
    private String code;

    @Getter
    private String message;

    LogSaveResultEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }


}
