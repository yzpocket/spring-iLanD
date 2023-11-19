package com.yzpocket.iland.exception;

public class EmailNotFoundException extends RuntimeException{
    public EmailNotFoundException(String msg) {
        super(msg);
    }
}
