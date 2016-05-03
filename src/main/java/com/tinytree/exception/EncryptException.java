package com.tinytree.exception;


public class EncryptException extends BaseException {

    private static final long serialVersionUID = -140297171695403882L;

    public EncryptException(String message) {
        super(message);
    }

    public EncryptException(Exception e) {
        super(e.getMessage(), e);
    }

}
