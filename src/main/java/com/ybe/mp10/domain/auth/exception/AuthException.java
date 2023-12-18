package com.ybe.mp10.domain.auth.exception;

import com.ybe.mp10.global.exception.GlobalException;
import org.springframework.http.HttpStatus;

public class AuthException extends GlobalException {
    public AuthException(String message, HttpStatus errorCode) {
        super(message, errorCode);
    }
}
