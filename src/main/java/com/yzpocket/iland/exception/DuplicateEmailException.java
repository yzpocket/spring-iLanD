package com.yzpocket.iland.exception;

public class DuplicateEmailException extends RuntimeException{
    public DuplicateEmailException(String msg){
        super(msg);
    }
}