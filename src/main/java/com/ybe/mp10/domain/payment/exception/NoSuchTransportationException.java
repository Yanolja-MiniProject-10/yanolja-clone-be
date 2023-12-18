package com.ybe.mp10.domain.payment.exception;

import com.ybe.mp10.global.exception.GlobalException;
import org.springframework.http.HttpStatus;

public class NoSuchTransportationException extends GlobalException {

    public NoSuchTransportationException(String message,
        HttpStatus errorCode) {
        super(message, errorCode);
    }

    public static NoSuchTransportationException create(){
        return new NoSuchTransportationException("올바른 운송 수단이 아닙니다.", HttpStatus.BAD_REQUEST);
    }
}
