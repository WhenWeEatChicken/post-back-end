package com.post.www.domain;

public class PostNotFoundException extends RuntimeException {

    public PostNotFoundException(Long idx){
        super("Could not find post "+idx);
    }
}
