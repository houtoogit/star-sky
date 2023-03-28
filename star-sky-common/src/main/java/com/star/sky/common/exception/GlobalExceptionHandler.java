package com.star.sky.common.exception;

import com.star.sky.common.entities.I18n;
import com.star.sky.common.result.ResponseResult;
import com.star.sky.common.utils.I18nUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
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
        log.error("exception info is: {}", e.getMessage(), e);
        String error_msg = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return ResponseResult.failed(HttpStatus.BAD_REQUEST, I18nUtil.getI18n(error_msg));
    }

    @ResponseBody
    @ExceptionHandler(value = BindException.class)
    public ResponseResult<I18n> bindException(BindException e) {
        log.error("exception info is: {}", e.getMessage(), e);
        String error_msg = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return ResponseResult.failed(HttpStatus.BAD_REQUEST, I18nUtil.getI18n(error_msg));
    }

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public ResponseResult<I18n> exception(Exception e) {
        log.error("exception info is: {}", e.getMessage(), e);
        return ResponseResult.failed(HttpStatus.INTERNAL_SERVER_ERROR, I18nUtil.getI18n("UNKNOWN_ERROR"));
    }

}
