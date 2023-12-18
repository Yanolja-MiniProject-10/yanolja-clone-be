package com.ybe.mp10.global.security;

import static com.ybe.mp10.global.common.constant.SecurityConstant.TOKEN_HEADER;

import com.ybe.mp10.global.security.exception.CustomAuthenticationEntryPoint;
import com.ybe.mp10.global.security.filter.CustomAuthenticationProcessingFilter;
import com.ybe.mp10.global.security.filter.JwtAuthenticationFilter;
import com.ybe.mp10.global.security.handler.CustomAuthenticationFailureHandler;
import com.ybe.mp10.global.security.handler.CustomAuthenticationSuccessHandler;
import com.ybe.mp10.global.security.provider.CustomProvider;
import com.ybe.mp10.global.security.provider.JwtProvider;
import com.ybe.mp10.global.util.JwtUtil;
import com.ybe.mp10.global.security.service.CustomUserDetailsService;
import com.ybe.mp10.global.security.service.LogoutService;
import com.ybe.mp10.global.security.service.RefreshTokenService;
import java.util.List;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;

    private final RefreshTokenService refreshTokenService;

    private final JwtUtil jwtUtil;

    private final LogoutService logoutService;

    private final CustomUserDetailsService customUserDetailsService;

    public SpringSecurityConfig(AuthenticationConfiguration authenticationConfiguration,
        RefreshTokenService refreshTokenService, JwtUtil jwtUtil, LogoutService logoutService, CustomUserDetailsService customUserDetailsService) {
        this.authenticationConfiguration = authenticationConfiguration;
        this.refreshTokenService = refreshTokenService;
        this.jwtUtil = jwtUtil;
        this.logoutService = logoutService;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .httpBasic(HttpBasicConfigurer::disable)
            .formLogin(FormLoginConfigurer::disable)
            .cors(httpSecurityCorsConfigurer ->
                httpSecurityCorsConfigurer
                    .configurationSource(corsConfigurationSource())
            )
            .csrf(CsrfConfigurer::disable)
            .sessionManagement(sessionManagementConfigurer ->
                sessionManagementConfigurer
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http
            .authorizeHttpRequests((auth) -> auth
                //.requestMatchers("/**").permitAll()
                .requestMatchers("/users").authenticated()
                .requestMatchers("/payment/instant").authenticated()
                .requestMatchers("/payment/purchase").authenticated()
                .requestMatchers(HttpMethod.GET, "/payment").authenticated()
                .requestMatchers("/carts").authenticated()
                .anyRequest().permitAll()
            )
            .addFilterBefore(new JwtAuthenticationFilter(authenticationManager()),
                UsernamePasswordAuthenticationFilter.class)
            .addFilterAfter(customAuthenticationProcessingFilter(),
                JwtAuthenticationFilter.class)
            .logout(logoutConfig -> {
                logoutConfig
                    .logoutUrl("/auth/logout")
                    .addLogoutHandler(logoutService)
                    .logoutSuccessHandler(
                        (request, response, authentication) -> SecurityContextHolder.clearContext());
            });

        http
            .exceptionHandling((exceptionHandling) ->
                exceptionHandling
                    .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
            );

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        //config.addAllowedOriginPattern("*");
        config.setAllowedOrigins(List.of("http://localhost:5173", "https://localhost:5173",
            "https://yanolja.vercel.app"));
        config.addAllowedHeader("*");
        config.setAllowedMethods(
            List.of("GET", "POST", "PATCH", "DELETE", "HEAD", "OPTIONS", "PUT"));
        config.setAllowCredentials(true);
        config.addExposedHeader(TOKEN_HEADER);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CustomProvider customFormLoginProvider() {
        return new CustomProvider(passwordEncoder(), customUserDetailsService);
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        ProviderManager authenticationManager = (ProviderManager) authenticationConfiguration.getAuthenticationManager();
        authenticationManager.getProviders().add(customFormLoginProvider());
        authenticationManager.getProviders().add(jwtProvider());
        return authenticationManager;
    }

    @Bean
    public JwtProvider jwtProvider() {
        return new JwtProvider(customUserDetailsService, jwtUtil);
    }

    @Bean
    public CustomAuthenticationProcessingFilter customAuthenticationProcessingFilter()
        throws Exception {
        CustomAuthenticationProcessingFilter filter = new CustomAuthenticationProcessingFilter(
            "/auth/login");
        filter.setAuthenticationManager(authenticationManager());
        filter.setAuthenticationFailureHandler(new CustomAuthenticationFailureHandler());
        filter.setAuthenticationSuccessHandler(
            new CustomAuthenticationSuccessHandler(refreshTokenService, jwtUtil));
        return filter;
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web ->
            web.ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                .requestMatchers("/favicon.ico", "/resources/**", "/error");
    }
}
