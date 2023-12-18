package com.ybe.mp10.domain.payment.exception;

import com.ybe.mp10.global.exception.GlobalException;
import org.springframework.http.HttpStatus;

public class NoSuchPaymentException extends GlobalException {


    public NoSuchPaymentException(String message, HttpStatus errorCode) {
        super(message, errorCode);
    }

    public static NoSuchPaymentException create(){
        return new NoSuchPaymentException("결제 정보가 없습니다", HttpStatus.BAD_REQUEST);
    }
}
