package com.numarics.service;

import com.numarics.dto.LoginDTO;
import com.numarics.dto.RegisterUserDTO;
import com.numarics.model.UserEntity;
import com.numarics.repository.UserRepository;
import com.numarics.service.impl.DefaultUserService;
import com.numarics.util.JwtUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DefaultUserServiceTest {

    @InjectMocks
    private DefaultUserService defaultUserService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private AuthenticationManager authenticationManager;

    @Test
    void registerUserAlreadyExistsTest() {
        RegisterUserDTO userDTO = new RegisterUserDTO();
        userDTO.setEmail("test@test.com");
        when(userRepository.findByEmail("test@test.com")).thenReturn(Optional.of(new UserEntity()));

        assertThrows(IllegalArgumentException.class, () -> defaultUserService.registerUser(userDTO, Optional.empty()));
    }

    @Test
    void loginUserTest() {
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setEmail("test@test.com");
        loginDTO.setPassword("password");

        UserEntity userEntity = new UserEntity();
        userEntity.setEmail("test@test.com");
        userEntity.setPassword("encodedPassword");

        when(userRepository.findByEmail("test@test.com")).thenReturn(Optional.of(userEntity));
        when(passwordEncoder.matches("password", "encodedPassword")).thenReturn(true);
        when(jwtUtil.generateToken(any())).thenReturn("generatedToken");

        String token = defaultUserService.loginUser(loginDTO);

        assertNotNull(token);
        verify(authenticationManager, times(1)).authenticate(any());
        verify(userRepository, times(1)).findByEmail(any());
        verify(passwordEncoder, times(1)).matches(any(), any());
        verify(jwtUtil, times(1)).generateToken(any());

    }

    @Test
    void loginUserInvalidCredentialsTest() {
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setEmail("test@test.com");
        loginDTO.setPassword("password");

        UserEntity userEntity = new UserEntity();
        userEntity.setEmail("test@test.com");
        userEntity.setPassword("encodedPassword");

        when(userRepository.findByEmail("test@test.com")).thenReturn(Optional.of(userEntity));
        when(passwordEncoder.matches("password", "encodedPassword")).thenReturn(false);

        String token = defaultUserService.loginUser(loginDTO);

        assertNull(token);
        verify(authenticationManager, times(1)).authenticate(any());
        verify(userRepository, times(1)).findByEmail(any());
        verify(passwordEncoder, times(1)).matches(any(), any());
        verify(jwtUtil, never()).generateToken(any());
    }
}
