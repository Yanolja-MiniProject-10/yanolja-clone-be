package com.ybe.mp10.global.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ybe.mp10.global.response.GlobalDataResponse;
import com.ybe.mp10.global.util.JwtUtil;
import com.ybe.mp10.global.security.service.RefreshTokenService;
import com.ybe.mp10.global.security.token.BodyToken;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final RefreshTokenService refreshTokenService;
    private final JwtUtil jwtUtil;

    public CustomAuthenticationSuccessHandler(RefreshTokenService refreshTokenService, JwtUtil jwtUtil) {
        this.refreshTokenService = refreshTokenService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication) throws IOException {
        // access-token
        String accessToken = jwtUtil.createToken(authentication.getName(),
            authentication.getAuthorities());

        // refresh-token
        String refreshToken = refreshTokenService.updateRefreshToken(authentication.getName(),
            jwtUtil.createRefreshToken(authentication.getName()));

        // response
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        objectMapper.writeValue(response.getWriter(),
            GlobalDataResponse.ok("login success", new BodyToken(accessToken, refreshToken)));
    }
}
