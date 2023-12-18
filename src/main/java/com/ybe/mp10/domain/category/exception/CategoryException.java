package com.ybe.mp10.domain.category.exception;

import com.ybe.mp10.global.exception.GlobalException;
import org.springframework.http.HttpStatus;

public class CategoryException extends GlobalException {
    public CategoryException(String message, HttpStatus errorCode) {
        super(message, errorCode);
    }
}
