package com.dayuanit.movie.exception;

public class BizException extends RuntimeException {

    public BizException(){

    }
    public BizException(String msg){
        super(msg);
    }
}
