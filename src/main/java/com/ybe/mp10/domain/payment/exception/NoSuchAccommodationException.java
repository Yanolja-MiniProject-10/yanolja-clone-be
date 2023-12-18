package com.ybe.mp10.domain.payment.exception;

import com.ybe.mp10.global.exception.GlobalException;
import org.springframework.http.HttpStatus;

public class NoSuchAccommodationException extends GlobalException {

    public NoSuchAccommodationException(String message, HttpStatus errorCode) {
        super(message, errorCode);
    }

    public static NoSuchAccommodationException create(){
        return new NoSuchAccommodationException("해당 숙소가 없습니다.", HttpStatus.BAD_REQUEST);
    }
}
