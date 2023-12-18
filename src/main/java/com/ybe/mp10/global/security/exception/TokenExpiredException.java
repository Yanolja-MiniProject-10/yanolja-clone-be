package com.ybe.mp10.global.security.exception;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.AuthenticationException;

public class TokenExpiredException extends AuthenticationException {

    private final Claims claims;

    public TokenExpiredException(Claims claims, String message) {
        super(message);
        this.claims = claims;
    }

    public Claims getClaims() {
        return claims;
    }
}
