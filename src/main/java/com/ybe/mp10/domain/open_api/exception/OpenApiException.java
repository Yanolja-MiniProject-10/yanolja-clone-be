package com.ybe.mp10.domain.open_api.exception;

import com.ybe.mp10.global.exception.GlobalException;
import org.springframework.http.HttpStatus;

public class OpenApiException extends GlobalException {
    public OpenApiException(String message, HttpStatus errorCode) {
        super(message, errorCode);
    }
}
