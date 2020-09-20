package com.post.www.application.exception;

public class PasswordWrongException extends RuntimeException{
    public PasswordWrongException() {
        super("Password is wrong");
    }
}
