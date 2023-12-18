package com.ybe.mp10.global.security.service;

import static com.ybe.mp10.global.common.constant.SecurityConstant.TOKEN_TYPE;
import static com.ybe.mp10.global.common.constant.SecurityConstant.USER_KEY;

import com.ybe.mp10.domain.auth.entity.RefreshToken;
import com.ybe.mp10.domain.auth.repository.RefreshTokenRepository;
import com.ybe.mp10.domain.user.exception.UserException;
import com.ybe.mp10.domain.user.model.User;
import com.ybe.mp10.domain.user.repository.UserRepository;
import com.ybe.mp10.global.security.exception.BadTokenException;
import com.ybe.mp10.global.security.exception.RefreshTokenException;
import com.ybe.mp10.global.util.JwtUtil;
import com.ybe.mp10.global.security.token.BodyToken;
import com.ybe.mp10.domain.auth.dto.request.ReissueRequest;
import io.jsonwebtoken.Claims;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    private final JwtUtil jwtUtil;

    public boolean isValidRefreshToken(String tokenValue, Long userId)
        throws RefreshTokenException {
        RefreshToken findRefreshToken = refreshTokenRepository.findByTokenValue(tokenValue)
            .orElseThrow(() -> new RefreshTokenException("No Such RefreshToken"));
        return findRefreshToken.getUserId().equals(userId);
    }

    @Transactional
    public String updateRefreshToken(String email, String refreshTokenValue) {
        Optional<User> user = userRepository.findByEmail(email);
        Optional<RefreshToken> refreshToken = refreshTokenRepository.findByUserId(
            user.get().getId());

        if (refreshToken.isPresent()) {
            refreshToken.get().update(user.get().getId(), refreshTokenValue);
            RefreshToken dbRefreshToken = refreshTokenRepository.saveAndFlush(refreshToken.get());

            log.info("DB refreshToken : " + dbRefreshToken.getTokenValue());
        } else {
            RefreshToken insertRefreshToken = refreshTokenRepository.saveAndFlush(
                RefreshToken.builder()
                    .userId(user.get().getId())
                    .tokenValue(refreshTokenValue)
                    .build());

            log.info("DB refreshToken : " + insertRefreshToken.getTokenValue());
        }

        return refreshTokenValue;
    }

    @Transactional
    public BodyToken renewJwtToken(ReissueRequest reissueToken) {
        try {
            log.info("old refreshToken : " + reissueToken.getRefreshToken());

            Claims claims = jwtUtil.verifyToken(reissueToken.getRefreshToken().replace(TOKEN_TYPE, ""));

            User user = userRepository.findByEmail(
                claims.get(USER_KEY, String.class)).orElseThrow(() -> new UserException("사용자가 없습니다.", HttpStatus.UNAUTHORIZED));
            List<GrantedAuthority> roles = new ArrayList<>();
            roles.add(new SimpleGrantedAuthority("USER"));

            String newAccessToken = null;
            String newRefreshToken = null;

            if (isValidRefreshToken(reissueToken.getRefreshToken().replace(TOKEN_TYPE, ""), user.getId())) {
                newAccessToken = jwtUtil.createToken(user.getEmail(),
                    roles);
                newRefreshToken = updateRefreshToken(user.getEmail(),
                    jwtUtil.createRefreshToken(user.getEmail()));

                log.info("new refreshToken : " + newRefreshToken);
            }

            return new BodyToken(newAccessToken, newRefreshToken);
        } catch (BadTokenException e) {
            throw new BadTokenException("유효하지 않은 RefreshToken 입니다.");
        } catch (RefreshTokenException e) {
            throw new BadTokenException("해당 RefreshToken이 존재하지 않습니다.");
        }
    }
}
