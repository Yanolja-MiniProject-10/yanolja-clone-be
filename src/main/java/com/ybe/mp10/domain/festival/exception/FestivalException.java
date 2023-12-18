package com.ybe.mp10.domain.festival.exception;

import com.ybe.mp10.global.exception.GlobalException;
import org.springframework.http.HttpStatus;

public class FestivalException extends GlobalException {
    public FestivalException(String message, HttpStatus errorCode) {
        super(message, errorCode);
    }
}
