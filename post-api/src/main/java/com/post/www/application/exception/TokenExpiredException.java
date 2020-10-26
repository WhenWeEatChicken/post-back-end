package com.post.www.application.exception;

public class TokenExpiredException extends RuntimeException{
    public TokenExpiredException(){
        super("JWT Token Expired!!");
    }
}
