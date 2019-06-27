package com.dayuanit.movie.exception;

public class LoginException extends RuntimeException{

    public LoginException(){}

    public LoginException(String msg){
        super(msg);
    }
}
