package com.ybe.mp10.global.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ybe.mp10.domain.auth.dto.request.LoginRequest;
import com.ybe.mp10.global.security.token.CustomAuthenticationToken;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.util.StringUtils;

public class CustomAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public CustomAuthenticationProcessingFilter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
        HttpServletResponse response)
        throws AuthenticationException, IOException {

        LoginRequest loginRequest = objectMapper.readValue(request.getReader(),
            LoginRequest.class);

        if (!StringUtils.hasText(loginRequest.getEmail()) || !StringUtils.hasText(
            loginRequest.getPassword())) {
            throw new UsernameNotFoundException("Username Or Password is Empty");
        }

        CustomAuthenticationToken authRequest = CustomAuthenticationToken.unauthenticated(
            loginRequest.getEmail(), loginRequest.getPassword());
        setDetails(request, authRequest);

        return getAuthenticationManager()
            .authenticate(authRequest);
    }

    protected void setDetails(HttpServletRequest request, CustomAuthenticationToken authRequest) {
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }
}
