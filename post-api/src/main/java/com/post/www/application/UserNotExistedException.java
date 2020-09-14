package com.post.www.application;

public class UserNotExistedException extends RuntimeException{
    public UserNotExistedException(Long idx) {
        super("User is not existed: " + idx);
    }
}
