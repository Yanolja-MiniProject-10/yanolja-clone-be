package com.ybe.mp10.domain.accommodation.exception;

import com.ybe.mp10.global.exception.GlobalException;
import org.springframework.http.HttpStatus;

public class AccommodationException extends GlobalException {
    public AccommodationException(String message, HttpStatus errorCode) {
        super(message, errorCode);
    }
}
