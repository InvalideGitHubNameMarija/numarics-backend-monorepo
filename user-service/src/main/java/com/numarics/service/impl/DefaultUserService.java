package com.numarics.service.impl;

import com.numarics.dto.LoginDTO;
import com.numarics.dto.RegisterUserDTO;
import com.numarics.model.Role;
import com.numarics.model.UserEntity;
import com.numarics.resource.UserRepository;
import com.numarics.service.UserService;
import com.numarics.util.JwtUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class DefaultUserService implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private JwtUserDetailService userDetailsService;

    @Override
    public UserEntity registerUser(RegisterUserDTO userDTO) {
        validateUsernameUnique(userDTO.getEmail());
        var user = UserEntity.builder()
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .email(userDTO.getEmail())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .role(Role.USER)
                .build();
        return userRepository.save(user);
    }

    @Override
    public String loginUser(LoginDTO loginDTO) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDTO.getEmail(),
                loginDTO.getPassword()));
        final UserDetails userDetails = userDetailsService.loadUserByUsername(loginDTO.getEmail());
        return (passwordEncoder.matches(loginDTO.getPassword(), userDetails.getPassword())) ?
                jwtUtil.generateToken(userDetails) : null;
    }

    private void validateUsernameUnique(String email) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("User with this email already exists!");
        }
    }
}