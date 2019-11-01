package com.acongfly.yscutils.exception;

/**
 * check exception
 */
public class CheckException extends RuntimeException {

    private String bizCode;

    public String getBizCode() {
        return bizCode;
    }

    public void setBizCode(String bizCode) {
        this.bizCode = bizCode;
    }

    public CheckException(String bizCode) {
        this.bizCode = bizCode;
    }

    public CheckException(String bizCode, Throwable cause) {
        super(null, cause);
        this.bizCode = bizCode;
    }

    public CheckException(String bizCode, String message) {
        super(message);
        this.bizCode = bizCode;
    }

    public CheckException(String bizCode, String message, Throwable cause) {
        super(message, cause);
        this.bizCode = bizCode;
    }
}
