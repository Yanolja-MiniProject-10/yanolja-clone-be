package com.ybe.mp10.domain.roomoption.exception;

import com.ybe.mp10.global.exception.GlobalException;
import org.springframework.http.HttpStatus;

public class RoomOptionException extends GlobalException {
    public RoomOptionException(String message, HttpStatus errorCode) {
        super(message, errorCode);
    }
}
