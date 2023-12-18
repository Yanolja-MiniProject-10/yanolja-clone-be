package com.ybe.mp10.global.security.service;

import static com.ybe.mp10.global.common.constant.SecurityConstant.TOKEN_HEADER;
import static com.ybe.mp10.global.common.constant.SecurityConstant.TOKEN_TYPE;

import com.ybe.mp10.domain.auth.entity.RefreshToken;
import com.ybe.mp10.domain.auth.repository.RefreshTokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication) {

        Optional<RefreshToken> refreshToken = refreshTokenRepository.findByTokenValue(
            request.getHeader(TOKEN_HEADER).replace(TOKEN_TYPE, ""));

        if (refreshToken.isPresent()) {
            refreshTokenRepository.delete(refreshToken.get());
        }
    }
}