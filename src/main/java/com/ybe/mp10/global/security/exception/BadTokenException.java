package com.ybe.mp10.global.security.exception;

import org.springframework.security.core.AuthenticationException;

public class BadTokenException extends AuthenticationException {
    public BadTokenException(String message) {
        super(message);
    }
}
