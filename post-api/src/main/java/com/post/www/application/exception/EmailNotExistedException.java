package com.post.www.application.exception;

public class EmailNotExistedException extends RuntimeException{
    public EmailNotExistedException(String email) {
        super("Email is not existed: " + email);
    }
}
