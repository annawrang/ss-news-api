package com.sisterside.ssnewsapi.exception;

public class ForbiddenException extends RuntimeException {
    public ForbiddenException(){
        super();
    }

    public ForbiddenException(String message){
        super(message);
    }
}
