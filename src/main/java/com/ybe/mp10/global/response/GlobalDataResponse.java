package com.ybe.mp10.global.response;


import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
@Getter
public class GlobalDataResponse<T> extends GlobalResponse{
    private final T data;

    public GlobalDataResponse(HttpStatus httpStatus, String message, T data) {
        super(httpStatus.value(), message);
        this.data = data;
    }

    public static <T> GlobalDataResponse<T> ok(String message, T data) {
        return new GlobalDataResponse<>(OK, message, data);
    }

    public static <T> GlobalDataResponse<T> created(String message, T data) {
        return new GlobalDataResponse<>(CREATED, message, data);
    }
}
