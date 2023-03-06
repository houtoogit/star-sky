package com.star.sky.common.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseResult<T> {

    private int code;
    private String message;
    private T data;

    public static <T> ResponseResult<T> success(T data) {
        return new ResponseResult<T>(ErrorCode.SUCCESS.getCode(), ErrorCode.SUCCESS.getMessage(), data);
    }

    public static <T> ResponseResult<T> success(T data, String message) {
        return new ResponseResult<T>(ErrorCode.SUCCESS.getCode(), message, data);
    }

    public static <T> ResponseResult<T> failed(IErrorCode errorCode) {
        return new ResponseResult<T>(errorCode.getCode(), errorCode.getMessage(), null);
    }

    public static <T> ResponseResult<T> failed(IErrorCode errorCode, String message) {
        return new ResponseResult<T>(errorCode.getCode(), message, null);
    }

    public static <T> ResponseResult<T> failed(String message) {
        return new ResponseResult<T>(ErrorCode.FAILED.getCode(), message, null);
    }

    public static <T> ResponseResult<T> failed() {
        return failed(ErrorCode.FAILED);
    }

    public static <T> ResponseResult<T> validateFailed() {
        return failed(ErrorCode.VALIDATE_FAILED);
    }

    public static <T> ResponseResult<T> validateFailed(String message) {
        return new ResponseResult<T>(ErrorCode.VALIDATE_FAILED.getCode(), message, null);
    }

    public static <T> ResponseResult<T> unauthorized(T data) {
        return new ResponseResult<T>(ErrorCode.UNAUTHORIZED.getCode(), ErrorCode.UNAUTHORIZED.getMessage(), data);
    }

    public static <T> ResponseResult<T> forbidden(T data) {
        return new ResponseResult<T>(ErrorCode.FORBIDDEN.getCode(), ErrorCode.FORBIDDEN.getMessage(), data);
    }

}
