package com.numarics.config;

import com.numarics.enums.Role;
import com.numarics.filter.JwtRequestFilter;
import com.numarics.filter.OrderAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

    private static final String ORDER_ENDPOINTS = "/api/v1/orders/**";
    private static final String V3_ENDPOINTS = "/order-service/v3/api-docs/**";
    private static final String SWAGGER_ENDPOINTS = "/order-service/swagger-ui/**";

    private final JwtRequestFilter jwtRequestFilter;
    private final OrderAuthorizationFilter orderAuthorizationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers(ORDER_ENDPOINTS).hasAnyRole(ADMIN_ROLE, USER_ROLE)
                                .requestMatchers(V3_ENDPOINTS, SWAGGER_ENDPOINTS)
                                .permitAll()
                                .anyRequest()
                                .authenticated())
                .sessionManagement(sessionManagement ->
                        sessionManagement
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterAfter(orderAuthorizationFilter, JwtRequestFilter.class);
        return http.build();
    }
}
