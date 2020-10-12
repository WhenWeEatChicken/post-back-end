package com.post.www.application.exception;

public class FileNotFoundException extends RuntimeException{
    public FileNotFoundException(Long idx){
        super("Could not find file "+idx);
    }
}
