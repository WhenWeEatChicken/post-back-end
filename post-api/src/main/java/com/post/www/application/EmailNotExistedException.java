package com.post.www.application;

public class EmailNotExistedException extends RuntimeException{
    public EmailNotExistedException(String email) {
        super("Email is not existed: " + email);
    }
}
