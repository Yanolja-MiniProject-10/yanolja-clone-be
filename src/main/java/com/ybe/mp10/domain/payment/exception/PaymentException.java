package com.ybe.mp10.domain.payment.exception;

import com.ybe.mp10.global.exception.GlobalException;
import org.springframework.http.HttpStatus;

public class PaymentException extends GlobalException {
    public PaymentException(String message, HttpStatus errorCode) {
        super(message, errorCode);
    }
}
