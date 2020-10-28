package com.post.www.application.exception;

public class CommentNotFoundException extends RuntimeException {

    public CommentNotFoundException(Long idx){
        super("Could not find comment "+idx);
    }
}
