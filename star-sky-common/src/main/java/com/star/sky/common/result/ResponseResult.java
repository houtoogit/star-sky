package com.star.sky.common.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseResult<T> {

    private int code;
    private String message;
    private T data;

    public static <T> ResponseResult<T> success(T data) {
        return new ResponseResult<T>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), data);
    }

    public static <T> ResponseResult<T> success(T data, String message) {
        return new ResponseResult<T>(HttpStatus.OK.value(), message, data);
    }

    public static <T> ResponseResult<T> failed(HttpStatus status) {
        return new ResponseResult<T>(status.value(), status.getReasonPhrase(), null);
    }

    public static <T> ResponseResult<T> failed(HttpStatus status, String message) {
        return new ResponseResult<T>(status.value(), message, null);
    }

    public static <T> ResponseResult<T> failed(String message) {
        return new ResponseResult<T>(HttpStatus.INTERNAL_SERVER_ERROR.value(), message, null);
    }

}
