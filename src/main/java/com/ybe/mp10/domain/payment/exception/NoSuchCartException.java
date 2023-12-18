package com.ybe.mp10.domain.payment.exception;

import com.ybe.mp10.global.exception.GlobalException;
import org.springframework.http.HttpStatus;

public class NoSuchCartException extends GlobalException {

    public NoSuchCartException(String message, HttpStatus errorCode) {
        super(message, errorCode);
    }

    public static NoSuchCartException create(){
        return new NoSuchCartException("장바구니가 없습니다.", HttpStatus.BAD_REQUEST);
    }
}
