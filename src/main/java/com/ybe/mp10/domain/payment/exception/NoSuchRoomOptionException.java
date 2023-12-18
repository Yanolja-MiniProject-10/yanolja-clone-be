package com.ybe.mp10.domain.payment.exception;

import com.ybe.mp10.global.exception.GlobalException;
import org.springframework.http.HttpStatus;

public class NoSuchRoomOptionException extends GlobalException {

    public NoSuchRoomOptionException(String message, HttpStatus errorCode) {
        super(message, errorCode);
    }

    public static NoSuchRoomOptionException create(){
        return new NoSuchRoomOptionException("방 옵션이 없습니다.", HttpStatus.BAD_REQUEST);
    }
}
