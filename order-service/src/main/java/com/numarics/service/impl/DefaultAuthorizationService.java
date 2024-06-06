package com.numarics.service.impl;

import com.numarics.enums.Role;
import com.numarics.service.AuthorizationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * Default implementation of {@link AuthorizationService} interface.
 */
@Slf4j
@Service
public class DefaultAuthorizationService implements AuthorizationService {

    private static final String ADMIN_ROLE = Role.ADMIN.name();

    @Override
    public boolean isAdmin(String userId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.debug("Checking if user {} is an admin", userId);
        return authentication.getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_" + ADMIN_ROLE));
    }

    @Override
    public boolean isAuthorized(String userId, String targetUserId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.debug("Checking if user {} is authorized to act on behalf of {}", userId, targetUserId);
        return userId.equals(targetUserId) || authentication.getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_" + ADMIN_ROLE));
    }
}
