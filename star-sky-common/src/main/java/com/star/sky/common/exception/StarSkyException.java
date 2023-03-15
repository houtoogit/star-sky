package com.star.sky.common.exception;

import org.springframework.http.HttpStatus;

public class StarSkyException extends RuntimeException {

    private HttpStatus status;

    public StarSkyException(HttpStatus status) {
        super(status.getReasonPhrase());
        this.status = status;
    }

    public StarSkyException(String message) {
        super(message);
    }

    public StarSkyException(Throwable cause) {
        super(cause);
    }

    public StarSkyException(String message, Throwable cause) {
        super(message, cause);
    }

    public HttpStatus getStatus() {
        return status;
    }

}
