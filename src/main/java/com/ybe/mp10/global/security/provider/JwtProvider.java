package com.ybe.mp10.global.security.provider;

import static com.ybe.mp10.global.common.constant.SecurityConstant.USER_KEY;

import com.ybe.mp10.global.security.exception.BadTokenException;
import com.ybe.mp10.global.security.service.CustomUserDetails;
import com.ybe.mp10.global.security.service.CustomUserDetailsService;
import com.ybe.mp10.global.security.token.JwtAuthenticationToken;
import com.ybe.mp10.global.security.token.UserPayload;
import com.ybe.mp10.global.util.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class JwtProvider implements AuthenticationProvider {
    private final CustomUserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    public JwtProvider(CustomUserDetailsService userDetailsService, JwtUtil jwtUtil) {
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Authentication authenticate(Authentication authentication)
        throws AuthenticationException {
        return processUserAuthentication(authentication);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthenticationToken.class.isAssignableFrom(authentication);
    }

    private Authentication processUserAuthentication(Authentication authentication)
        throws AuthenticationException {
        String accessToken = (String) authentication.getPrincipal();
        JwtAuthenticationToken authResult = null;

        try{
            Claims claims = jwtUtil.verifyToken(accessToken);
            CustomUserDetails userDetails = (CustomUserDetails) userDetailsService.loadUserByUsername(
                claims.get(USER_KEY, String.class));
            UserPayload userPayLoad = new UserPayload(userDetails.getUserId(),
                userDetails.getUsername());
            authResult = JwtAuthenticationToken.authenticated(userPayLoad,
                null, userDetails.getAuthorities());
        }catch (JwtException e) {
            throw new BadTokenException(e.getMessage());
        }

        return authResult;
    }
}
