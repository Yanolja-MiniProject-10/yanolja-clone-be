package com.ybe.mp10.global.handler;

import com.ybe.mp10.global.exception.GlobalException;
import com.ybe.mp10.global.response.ErrorResponse;
import com.ybe.mp10.global.security.exception.BadTokenException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Arrays;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
@Profile("test")
@Slf4j
public class GlobalTestExceptionHandler {
    @ExceptionHandler(GlobalException.class)
    protected final ResponseEntity<ErrorResponse> handleGlobalException(GlobalException ex,
                                                                        WebRequest request
    ) {

//        log.error(ex.getMessage());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .errorMessage("예외가 발생했습니다.")
                .status(409)
                .build();

        return new ResponseEntity<>(errorResponse,CONFLICT);
    }


    @ExceptionHandler(Exception.class)
    protected final ResponseEntity<ErrorResponse> handleException(Exception ex, WebRequest request
    ) {

//        log.error(ex.getMessage());

        return new ResponseEntity<>(ErrorResponse.internalServerError(ex.getMessage()),
                INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected final ResponseEntity<ErrorResponse> methodValidException(MethodArgumentNotValidException ex, WebRequest request){
//        log.error("handling {}, message : {}", ex.getClass().toString(), ex.getMessage());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .errorMessage("validation 예외가 발생했습니다.")
                .status(BAD_REQUEST.value())
                .build();

        return new ResponseEntity<>(errorResponse, BAD_REQUEST);
    }

    @ExceptionHandler(BadTokenException.class)
    protected final ResponseEntity<ErrorResponse> badTokenException(BadTokenException ex, WebRequest request){
        log.error("handling {}, message : {}", ex.getClass().toString(), ex.getMessage());

        ErrorResponse errorResponse = ErrorResponse.builder()
            .errorMessage(ex.getMessage())
            .status(HttpStatus.METHOD_NOT_ALLOWED.value())
            .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.METHOD_NOT_ALLOWED);
    }
}
