package com.post.www.application.exception;

public class PostNotFoundException extends RuntimeException {

    public PostNotFoundException(Long idx){
        super("Could not find post "+idx);
    }
}
