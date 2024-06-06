package com.numarics.config;

import com.numarics.enums.Role;
import com.numarics.filter.JwtRequestFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private static final String ADMIN_ROLE = Role.ADMIN.name();
    private static final String USER_ROLE = Role.USER.name();

    private static final String PRODUCT_ENDPOINTS = "/api/v1/products/**";
    private static final String V3_ENDPOINTS = "/product-service/v3/api-docs/**";
    private static final String SWAGGER_ENDPOINTS = "/product-service/swagger-ui/**";

    private final JwtRequestFilter jwtRequestFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers(HttpMethod.POST, PRODUCT_ENDPOINTS).hasRole(ADMIN_ROLE)
                                .requestMatchers(HttpMethod.GET, PRODUCT_ENDPOINTS).hasAnyRole(ADMIN_ROLE, USER_ROLE)
                                .requestMatchers(HttpMethod.PUT, PRODUCT_ENDPOINTS).hasRole(ADMIN_ROLE)
                                .requestMatchers(HttpMethod.DELETE, PRODUCT_ENDPOINTS).hasRole(ADMIN_ROLE)
                                .requestMatchers(V3_ENDPOINTS, SWAGGER_ENDPOINTS)
                                .permitAll()
                                .anyRequest()
                                .authenticated())
                .sessionManagement(sessionManagement ->
                        sessionManagement
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
