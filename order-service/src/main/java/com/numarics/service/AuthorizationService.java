package com.numarics.service;

/**
 * Service interface for handling authorization-related operations
 */
public interface AuthorizationService {

    /**
     * Checks if the user has an ADMIN role.
     *
     * @param userId The ID of the user to check
     * @return true if the user is an admin, false otherwise
     */
    boolean isAdmin(String userId);

    /**
     * Checks if the user is authorized to perform an action on behalf of another user.
     *
     * @param userId The ID of the user performing the action
     * @param targetUserId The ID of the user who is the target of the action
     * @return true if the user is authorized, false otherwise
     */
    boolean isAuthorized(String userId, String targetUserId);
}
