package com.ybe.mp10.domain.region.exception;

import com.ybe.mp10.global.exception.GlobalException;
import org.springframework.http.HttpStatus;

public class RegionException extends GlobalException {
    public RegionException(String message, HttpStatus errorCode) {
        super(message, errorCode);
    }
}
