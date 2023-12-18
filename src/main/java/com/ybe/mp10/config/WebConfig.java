package com.ybe.mp10.config;

import static com.ybe.mp10.global.common.constant.SecurityConstant.TOKEN_HEADER;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                //.allowedOriginPatterns("*")
                .allowedOrigins("http://localhost:5173")
                .allowedOrigins("https://localhost:5173")
                .allowedOrigins("https://yanolja.vercel.app")
                .allowedMethods("GET", "POST", "PATCH", "DELETE", "HEAD", "OPTIONS", "PUT")
                .allowCredentials(true)
                .allowedHeaders("*")
                .exposedHeaders(TOKEN_HEADER)
                .maxAge(3000);
    }
}
