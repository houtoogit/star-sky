package com.star.sky.common.exception;

import lombok.Data;
import com.star.sky.common.entities.I18n;
import org.springframework.http.HttpStatus;

@Data
public class StarSkyException extends RuntimeException {

    private I18n i18n;
    private HttpStatus status;

    public StarSkyException(I18n i18n) {
        this.i18n = i18n;
    }

    public StarSkyException(HttpStatus status) {
        super(status.getReasonPhrase());
        this.status = status;
    }

    public StarSkyException(HttpStatus status, I18n i18n) {
        this.i18n = i18n;
        this.status = status;
    }

    public StarSkyException(Throwable cause) {
        super(cause);
    }

    public StarSkyException(String message, Throwable cause) {
        super(message, cause);
    }

}
