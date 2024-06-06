package com.numarics.service;

public interface AuthorizationService {

    boolean isAdmin(String userId);

    boolean isAuthorized(String userId, String targetUserId);
}
