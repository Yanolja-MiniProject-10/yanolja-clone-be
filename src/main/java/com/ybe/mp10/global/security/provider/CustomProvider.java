package com.ybe.mp10.global.security.provider;

import com.sun.security.auth.UserPrincipal;
import com.ybe.mp10.global.security.service.CustomUserDetails;
import com.ybe.mp10.global.security.service.CustomUserDetailsService;
import com.ybe.mp10.global.security.token.CustomAuthenticationToken;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;

public class CustomProvider implements AuthenticationProvider {
    private final PasswordEncoder passwordEncoder;
    private final CustomUserDetailsService userDetailsService;

    public CustomProvider(PasswordEncoder passwordEncoder,
        CustomUserDetailsService userDetailsService) {
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication)
        throws AuthenticationException {
        String username = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

        CustomUserDetails userDetails = (CustomUserDetails) userDetailsService.loadUserByUsername(
            username);

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("BadCredentialsException");
        }

        UserPrincipal userPrincipal = new UserPrincipal(userDetails.getUsername());

        CustomAuthenticationToken result = CustomAuthenticationToken.authenticated(userPrincipal,
            null,
            userDetails.getAuthorities());
        result.setDetails(authentication.getDetails());
        return result;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return CustomAuthenticationToken.class.isAssignableFrom(authentication);
    }

}
