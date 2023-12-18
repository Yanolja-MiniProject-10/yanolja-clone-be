package com.ybe.mp10.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class GlobalException extends RuntimeException {

    private final HttpStatus errorCode;

    public GlobalException(String message, HttpStatus errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
