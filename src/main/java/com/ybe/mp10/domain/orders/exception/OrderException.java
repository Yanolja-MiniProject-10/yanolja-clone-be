package com.ybe.mp10.domain.orders.exception;

import com.ybe.mp10.global.exception.GlobalException;
import org.springframework.http.HttpStatus;

public class OrderException extends GlobalException {

    public OrderException(String message, HttpStatus errorCode) {
        super(message, errorCode);
    }
}
