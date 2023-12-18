package com.ybe.mp10.global.security.exception;

import org.springframework.security.core.AuthenticationException;

public class RefreshTokenException extends AuthenticationException {

    public RefreshTokenException(String message) {
        super(message);
    }
}
