package com.numarics.config;

import feign.RequestInterceptor;
import java.util.Objects;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Configuration
public class FeignClientConfig {
    private static final String BEARER_PREFIX = "Bearer ";

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (Objects.nonNull(authentication) && Objects.nonNull(authentication.getCredentials())) {
                String token = authentication.getCredentials().toString();
                requestTemplate.header(HttpHeaders.AUTHORIZATION, BEARER_PREFIX + token);
            }
        };
    }
}
