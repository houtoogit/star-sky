package com.star.sky.common.exception;

import com.star.sky.common.result.ResponseResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = ApiException.class)
    public ResponseResult handle(ApiException e) {
        if (e.getErrorCode() != null) {
            return ResponseResult.failed(e.getErrorCode());
        }
        return ResponseResult.failed(e.getMessage());
    }

}
