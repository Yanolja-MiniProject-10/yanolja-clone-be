package com.ybe.mp10.global.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ybe.mp10.global.response.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
        HttpServletResponse response,
        AuthenticationException ex)
        throws IOException {

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        String errorMessage = "Authentication Failed";
        if (ex instanceof BadCredentialsException) {
            errorMessage = "Invalid Username Or Password";
        } else if (ex instanceof InsufficientAuthenticationException) {
            errorMessage = "Invalid Secret Key";
        } else if (ex instanceof UsernameNotFoundException) {
            errorMessage = ex.getMessage();
        }

        objectMapper.writeValue(response.getWriter(), ErrorResponse.unAuthorized(errorMessage));
    }
}
