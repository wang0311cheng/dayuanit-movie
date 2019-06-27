package com.dayuanit.movie.controller;

import com.dayuanit.movie.dto.ResponseDTO;
import com.dayuanit.movie.exception.BizException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public abstract class BaseController {

    Logger logger = LoggerFactory.getLogger(BaseController.class);

    @ExceptionHandler(BizException.class)
    @ResponseBody
    public ResponseDTO processBizException(BizException be){
        logger.error(be.getMessage(),be);
        return ResponseDTO.fail(be.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseDTO processException(Exception e){
        logger.error(e.getMessage(),e);
        return ResponseDTO.fail(e.getMessage());
    }
}
