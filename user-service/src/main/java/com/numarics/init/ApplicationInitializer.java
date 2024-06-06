package com.numarics.init;

import com.numarics.dto.RegisterUserDTO;
import com.numarics.enums.Role;
import com.numarics.service.UserService;
import jakarta.annotation.PostConstruct;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApplicationInitializer {

    @Value("${admin.default.email:admin@mail.com}")
    private String email;

    @Value("${admin.default.password:admin}")
    private String password;

    private final UserService userService;

    @PostConstruct
    public void initialize() {
        RegisterUserDTO userDTO = new RegisterUserDTO();
        userDTO.setFirstName("admin");
        userDTO.setLastName("admin");
        userDTO.setEmail(email);
        userDTO.setPassword(password);
        userService.registerUser(userDTO, Optional.of(Role.ADMIN));
    }
}
