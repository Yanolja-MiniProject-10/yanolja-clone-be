package com.ybe.mp10.domain.payment.exception;

import com.ybe.mp10.global.exception.GlobalException;
import org.springframework.http.HttpStatus;

public class NoSuchCartProductException extends GlobalException {

    public NoSuchCartProductException(String message, HttpStatus errorCode) {
        super(message, errorCode);
    }

    public static NoSuchCartProductException create(){
        return new NoSuchCartProductException("장바구니 상품이 없습니다.", HttpStatus.BAD_REQUEST);
    }
}
