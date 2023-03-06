package com.star.sky.common.exception;

import com.star.sky.common.result.ErrorCode;
import com.star.sky.common.result.IErrorCode;

public class ApiException extends RuntimeException {

    private IErrorCode errorCode;

    public ApiException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ApiException(String message) {
        super(message);
    }

    public ApiException(Throwable cause) {
        super(cause);
    }

    public ApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public IErrorCode getErrorCode() {
        return errorCode;
    }

}
