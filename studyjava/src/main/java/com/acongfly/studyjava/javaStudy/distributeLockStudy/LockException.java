package com.acongfly.studyjava.javaStudy.distributeLockStudy;

public class LockException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public LockException(String e) {
        super(e);
    }

    public LockException(Exception e) {
        super(e);
    }
}