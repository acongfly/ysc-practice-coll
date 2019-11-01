package com.acongfly.study.enums;

/**
 * @program: ysc-practice-coll
 * @description: 错误枚举
 * @author: shicong yang
 * @create: 2019-07-07 17:30
 **/
public enum ErrorEnum {

    ERROR_CHECK_VALIDATE("400", "check validate error"), ERROR_REQ_NULL("401", "request param is null"),;

    private String code;

    private String message;

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    ErrorEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

}
