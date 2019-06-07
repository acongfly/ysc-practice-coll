package com.acongfly.studyjava.javaStudy.rxJavaStudy;

public class BizException extends RuntimeException {

    private String bizCode;

    public String getBizCode() {
        return bizCode;
    }

    public void setBizCode(String bizCode) {
        this.bizCode = bizCode;
    }

    public BizException(String bizCode) {
        this.bizCode = bizCode;
    }

    public BizException(String bizCode, Throwable cause) {
        super(null, cause);
        this.bizCode = bizCode;
    }

    public BizException(String bizCode, String message) {
        super(message);
        this.bizCode = bizCode;
    }

    public BizException(String bizCode, String message, Throwable cause) {
        super(message, cause);
        this.bizCode = bizCode;
    }

    public static void fastException(boolean b, String bizCode, String message, Throwable cause) {

        if (!b) {

            throw new BizException(bizCode, message, cause);
        }
    }

    public static void fastException(boolean b, String bizCode, String message) {

        if (!b) {

            throw new BizException(bizCode, message);
        }
    }

    public static void fastException(boolean b, String bizCode) {

        if (!b) {

            throw new BizException(bizCode);
        }
    }
}
