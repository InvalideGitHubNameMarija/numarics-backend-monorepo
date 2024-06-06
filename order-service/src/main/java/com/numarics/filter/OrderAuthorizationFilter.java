package com.numarics.filter;

import com.numarics.model.OrderEntity;
import com.numarics.service.AuthorizationService;
import com.numarics.service.OrderService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.HttpMethod;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class OrderAuthorizationFilter extends OncePerRequestFilter {

    private static final String ORDER_ENDPOINT_PREFIX = "/api/v1/orders/";

    private final OrderService orderService;
    private final AuthorizationService  authorizationService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();
        String method = request.getMethod();

        if (path.startsWith(ORDER_ENDPOINT_PREFIX) && (method.equals(HttpMethod.GET) || method.equals(HttpMethod.PUT)
                || method.equals(HttpMethod.DELETE))) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userId = authentication.getName();

            Long orderId = extractOrderIdFromPath(path);

            if (Objects.nonNull(orderId)) {
                Optional<OrderEntity> order = orderService.getOrderById(orderId);

                if (order.isPresent()) {
                    String orderCreatorId = String.valueOf(order.get().getUserId());

                    if (!authorizationService.isAuthorized(userId, orderCreatorId)) {
                        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                        return;
                    }
                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    return;
                }
            } else {
                String userIdParam = request.getParameter("userId");
                if (StringUtils.isNotEmpty(userIdParam)) {
                    if (!authorizationService.isAuthorized(userId, userIdParam)) {
                        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                        return;
                    }
                } else {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    return;
                }
            }
        }

        filterChain.doFilter(request, response);
    }

    private Long extractOrderIdFromPath(String path) {
        String[] parts = path.split("/");
        try {
            return Long.parseLong(parts[parts.length - 1]);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
