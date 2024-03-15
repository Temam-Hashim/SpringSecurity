package com.temx.security.exception;

public class RequestBodyRequired extends RuntimeException {

    public RequestBodyRequired(String message){
        super(message);
    }


}
