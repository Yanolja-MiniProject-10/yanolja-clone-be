package com.ybe.mp10.domain.cart.exception;

import com.ybe.mp10.global.exception.GlobalException;
import org.springframework.http.HttpStatus;

public class CartException extends GlobalException {
    public CartException(String message, HttpStatus errorCode) {
        super(message, errorCode);
    }
}
