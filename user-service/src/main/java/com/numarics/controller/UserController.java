package com.numarics.controller;

import static com.numarics.util.ApiDocumentation.LOGIN_OPERATION_DESCRIPTION;
import static com.numarics.util.ApiDocumentation.LOGIN_OPERATION_SUMMARY;
import static com.numarics.util.ApiDocumentation.LOGIN_RESPONSE_200_DESCRIPTION;
import static com.numarics.util.ApiDocumentation.LOGIN_RESPONSE_401_DESCRIPTION;
import static com.numarics.util.ApiDocumentation.REGISTER_OPERATION_DESCRIPTION;
import static com.numarics.util.ApiDocumentation.REGISTER_OPERATION_SUMMARY;
import static com.numarics.util.ApiDocumentation.REGISTER_RESPONSE_201_DESCRIPTION;
import static com.numarics.util.ApiDocumentation.REGISTER_RESPONSE_400_DESCRIPTION;
import static com.numarics.util.ApiDocumentation.USERS_TAG;

import com.numarics.dto.LoginDTO;
import com.numarics.dto.RegisterUserDTO;
import com.numarics.dto.UserDTO;
import com.numarics.model.UserEntity;
import com.numarics.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    @PostMapping("/register")
    @Operation(summary = REGISTER_OPERATION_SUMMARY,
            description = REGISTER_OPERATION_DESCRIPTION,
            tags = USERS_TAG)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = REGISTER_RESPONSE_201_DESCRIPTION),
            @ApiResponse(responseCode = "400", description = REGISTER_RESPONSE_400_DESCRIPTION)
    })
    public ResponseEntity<UserDTO> registerUser(@Valid @RequestBody final RegisterUserDTO registerUserDTO) {
        UserEntity registeredUser = userService.registerUser(registerUserDTO, Optional.empty());
        return ResponseEntity.status(HttpStatus.CREATED).body(modelMapper.map(registeredUser, UserDTO.class));
    }

    @PostMapping(value = "/login")
    @Operation(summary = LOGIN_OPERATION_SUMMARY,
            description = LOGIN_OPERATION_DESCRIPTION,
            tags = USERS_TAG)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = LOGIN_RESPONSE_200_DESCRIPTION),
            @ApiResponse(responseCode = "401", description = LOGIN_RESPONSE_401_DESCRIPTION)
    })
    public ResponseEntity<String> createAuthenticationToken(@Valid @RequestBody final LoginDTO user) {
        return ResponseEntity.ok(userService.loginUser(user));
    }
}
