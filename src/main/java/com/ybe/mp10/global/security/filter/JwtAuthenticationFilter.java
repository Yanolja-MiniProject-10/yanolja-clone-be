package com.ybe.mp10.global.security.filter;

import static com.ybe.mp10.global.common.constant.SecurityConstant.TOKEN_HEADER;
import static com.ybe.mp10.global.common.constant.SecurityConstant.TOKEN_TYPE;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ybe.mp10.global.response.ErrorResponse;
import com.ybe.mp10.global.security.exception.BadTokenException;
import com.ybe.mp10.global.security.token.JwtAuthenticationToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.log.LogMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder
        .getContextHolderStrategy();

    private final AuthenticationManager authenticationManager;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain chain)
        throws ServletException, IOException, AuthenticationException {
        if (this.securityContextHolderStrategy.getContext().getAuthentication() != null) {
            this.logger.debug(LogMessage
                .of(() ->
                    "SecurityContextHolder not populated with jwt token, as it already contained: '"
                        + this.securityContextHolderStrategy.getContext().getAuthentication()
                        + "'"));
            chain.doFilter(request, response);
            return;
        }

        String accessToken = request.getHeader(TOKEN_HEADER);
        log.info(request.getMethod() + " " + request.getRequestURI());
        log.info("accessToken : " + accessToken);

        if (accessToken != null && !isReissue(request)) {
            String jwtToken = accessToken.replace(TOKEN_TYPE, "");

            JwtAuthenticationToken jwtAuth = JwtAuthenticationToken.unauthenticated(
                jwtToken, null);
            try {
                Authentication authenticate = this.authenticationManager.authenticate(jwtAuth);
                SecurityContext context = securityContextHolderStrategy.createEmptyContext();
                context.setAuthentication(authenticate);
                this.securityContextHolderStrategy.setContext(context);
                chain.doFilter(request, response);
            } catch (BadTokenException e) {
                onAuthenticationFailure(request, response, e);
            }
        } else {
            chain.doFilter(request, response);
        }
    }

    private boolean isReissue(HttpServletRequest request) {
        return request.getMethod().equals("POST") &&
            request.getRequestURI().equals("/auth/reissues");
    }

    public void onAuthenticationFailure(HttpServletRequest request,
        HttpServletResponse response, BadTokenException ex)
        throws IOException {

        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        String errorMessage = "Token Reissue";

        objectMapper.writeValue(response.getWriter(), ErrorResponse.forbidden(errorMessage));
    }
}
