package com.star.sky.common.exception;

import com.star.sky.common.entities.I18n;
import com.star.sky.common.result.ResponseResult;
import com.star.sky.common.utils.I18nUtil;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = StarSkyException.class)
    public ResponseResult<I18n> starSkyException(StarSkyException e) {
        I18n i18n = e.getI18n();
        HttpStatus status = e.getStatus();
        if (i18n != null && status != null) {
            return ResponseResult.failed(status, i18n);
        }
        return ResponseResult.failed(i18n);
    }

    @ResponseBody
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseResult<I18n> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        String error_msg = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return ResponseResult.failed(HttpStatus.BAD_REQUEST, I18nUtil.getI18n(error_msg));
    }

    @ResponseBody
    @ExceptionHandler(value = BindException.class)
    public ResponseResult<I18n> bindException(BindException e) {
        String error_msg = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return ResponseResult.failed(HttpStatus.BAD_REQUEST, I18nUtil.getI18n(error_msg));
    }

}
