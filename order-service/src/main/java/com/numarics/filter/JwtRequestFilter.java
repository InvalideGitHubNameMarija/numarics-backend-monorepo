package com.numarics.filter;

import com.numarics.client.UserServiceClient;
import com.numarics.client.dto.UserInfoDTO;
import com.numarics.exception.ServiceUnavailableException;
import feign.FeignException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Objects;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION_HEADER = HttpHeaders.AUTHORIZATION;
    private static final String BEARER_PREFIX = "Bearer ";

    private final UserServiceClient userServiceClient;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException,
            IOException {
        final String requestAuthHeader = request.getHeader(AUTHORIZATION_HEADER);

        final String jwt;
        if (StringUtils.isEmpty(requestAuthHeader) || !requestAuthHeader.startsWith(BEARER_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }
        jwt = requestAuthHeader.substring(BEARER_PREFIX.length());
        try {
            UserInfoDTO userInfo = userServiceClient.validateToken(jwt).getBody();
            if (Objects.nonNull(userInfo)) {
                UserDetails userDetails = new User(
                        String.valueOf(userInfo.getId()),
                        "",
                        Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + userInfo.getRole())));
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails, jwt, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        } catch (FeignException e) {
            if (e.status() == HttpStatus.UNAUTHORIZED.value()) {
                throw new RuntimeException("Unauthorized: Invalid token");
            }
            if (e.status() == HttpStatus.SERVICE_UNAVAILABLE.value()) {
                throw new ServiceUnavailableException("Service unavailable: Unable to reach user service");
            }
        } catch (Exception e) {
            throw new RuntimeException("Internal server error");
        }

        filterChain.doFilter(request, response);
    }
}
