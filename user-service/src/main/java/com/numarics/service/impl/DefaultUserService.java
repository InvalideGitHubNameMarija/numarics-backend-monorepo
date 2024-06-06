package com.numarics.service.impl;

import com.numarics.dto.LoginDTO;
import com.numarics.dto.RegisterUserDTO;
import com.numarics.model.CustomUserDetails;
import com.numarics.enums.Role;
import com.numarics.model.UserEntity;
import com.numarics.repository.UserRepository;
import com.numarics.service.UserService;
import com.numarics.util.JwtUtil;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Default implementation of {@link UserService} interface.
 */
@Slf4j
@Service
@AllArgsConstructor
public class DefaultUserService implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final JwtUserDetailService jwtUserDetailService;

    @Override
    public UserEntity registerUser(RegisterUserDTO userDTO, Optional<Role> role) {
        validateUsernameUnique(userDTO.getEmail());
        Role userRole = role.orElse(Role.USER);
        UserEntity user = UserEntity.builder()
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .email(userDTO.getEmail())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .role(userRole)
                .build();
        log.info("Registering new user: {}", user.getEmail());
        return userRepository.save(user);
    }

    @Override
    public String loginUser(LoginDTO loginDTO) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDTO.getEmail(),
                loginDTO.getPassword()));

        return userRepository.findByEmail(loginDTO.getEmail())
                .filter(userEntity -> passwordEncoder.matches(loginDTO.getPassword(), userEntity.getPassword()))
                .map(userEntity -> {
                    CustomUserDetails customUserDetails = new CustomUserDetails(userEntity);
                    String token = jwtUtil.generateToken(customUserDetails);
                    log.info("User {} logged in successfully", loginDTO.getEmail());
                    return token;
                })
                .orElseGet(() -> {
                    log.warn("Login attempt failed for user: {}", loginDTO.getEmail());
                    return null;
                });
    }

    @Override
    public UserEntity validateToken(String token) {
        String username = jwtUtil.extractUsername(token);
        UserDetails userDetails = jwtUserDetailService.loadUserByUsername(username);

        if (!jwtUtil.isTokenValid(token, userDetails)) {
            log.error("Token {} is not valid", token);
            throw new RuntimeException("Token is not valid");
        }

        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "Username: " + username + " not found in the database!"));
    }

    private void validateUsernameUnique(String email) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("User with this email already exists!");
        }
    }
}
