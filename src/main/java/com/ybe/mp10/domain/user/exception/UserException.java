package com.ybe.mp10.domain.user.exception;

import com.ybe.mp10.global.exception.GlobalException;
import org.springframework.http.HttpStatus;

public class UserException extends GlobalException {
    public UserException(String message, HttpStatus errorCode) {
        super(message, errorCode);
    }
}
