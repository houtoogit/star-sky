package com.star.sky.common.exception;

import com.star.sky.common.result.ResponseResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = StarSkyException.class)
    public ResponseResult<String> handle(StarSkyException e) {
        if (e.getStatus() != null) {
            return ResponseResult.failed(e.getStatus());
        }
        return ResponseResult.failed(e.getMessage());
    }

}
