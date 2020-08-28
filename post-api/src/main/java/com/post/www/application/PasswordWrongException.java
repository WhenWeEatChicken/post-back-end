package com.post.www.application;

public class PasswordWrongException extends RuntimeException{
    public PasswordWrongException() {
        super("Password is wrong");
    }
}
