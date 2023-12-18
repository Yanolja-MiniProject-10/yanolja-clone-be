package com.ybe.mp10.global.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static org.apache.http.HttpStatus.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
    private int status;
    private String errorMessage;

    public static ErrorResponse internalServerError(String errorMessage) {

        return new ErrorResponse(SC_INTERNAL_SERVER_ERROR, errorMessage);
    }

    public static ErrorResponse badRequest(String errorMessage) {
        return new ErrorResponse(SC_BAD_REQUEST, errorMessage);
    }

    public static ErrorResponse notFound(String errorMessage) {
        return new ErrorResponse(SC_NOT_FOUND, errorMessage);
    }

    public static ErrorResponse conflict(String errorMessage) {
        return new ErrorResponse(SC_CONFLICT, errorMessage);
    }

    public static ErrorResponse forbidden(String errorMessage) {
        return new ErrorResponse(SC_FORBIDDEN, errorMessage);
    }

    public static ErrorResponse unAuthorized(String errorMessage) {
        return new ErrorResponse(SC_UNAUTHORIZED, errorMessage);
    }
}
