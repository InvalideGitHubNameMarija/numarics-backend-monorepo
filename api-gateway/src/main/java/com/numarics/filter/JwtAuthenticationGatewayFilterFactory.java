package com.numarics.filter;

import com.numarics.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationGatewayFilterFactory extends AbstractGatewayFilterFactory<Object> {

    private static final String AUTHORIZATION_HEADER = HttpHeaders.AUTHORIZATION;
    private static final String BEARER_PREFIX = "Bearer ";

    private final JwtUtil jwtUtil;

    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            String token = extractTokenFromRequest(exchange.getRequest());
            if (StringUtils.isEmpty(token) || !jwtUtil.isTokenValid(token)) {
                return handleError(exchange);
            }
            return chain.filter(exchange);
        };
    }

    private String extractTokenFromRequest(ServerHttpRequest request) {
        HttpHeaders headers = request.getHeaders();
        String authHeader = headers.getFirst(AUTHORIZATION_HEADER);
        if (StringUtils.isNotEmpty(authHeader) && authHeader.startsWith(BEARER_PREFIX)) {
            return authHeader.substring(BEARER_PREFIX.length());
        }
        return null;
    }

    private Mono<Void> handleError(ServerWebExchange exchange) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }
}
