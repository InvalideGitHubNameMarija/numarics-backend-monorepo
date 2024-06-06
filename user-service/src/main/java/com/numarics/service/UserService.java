package com.numarics.service;

import com.numarics.dto.LoginDTO;
import com.numarics.dto.RegisterUserDTO;
import com.numarics.enums.Role;
import com.numarics.model.UserEntity;
import java.util.Optional;

/**
 * The UserService interface defines methods for user-related operations
 */
public interface UserService {

    /**
     * Registers a new user
     *
     * @param userDTO The DTO containing user registration information
     * @param role    The optional role of the user
     * @return Registered user entity
     */
    UserEntity registerUser(RegisterUserDTO userDTO, Optional<Role> role);

    /**
     * Logs in a user
     *
     * @param loginDTO The DTO containing email and password
     * @return The JWT token for the logged-in user
     */
    String loginUser(LoginDTO loginDTO);

    /**
     * Validates a JWT token and retrieves the corresponding user entity
     *
     * @param token The JWT token to validate
     * @return The user entity associated with the token, or null if the token is invalid
     */
    UserEntity validateToken(String token);
}
