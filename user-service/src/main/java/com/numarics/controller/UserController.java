package com.numarics.controller;

import static com.numarics.util.UserApiDocumentation.*;

import com.numarics.dto.LoginDTO;
import com.numarics.dto.RegisterUserDTO;
import com.numarics.dto.UserDTO;
import com.numarics.dto.UserInfoDTO;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
            @ApiResponse(responseCode = STATUS_201, description = REGISTER_RESPONSE_201_DESCRIPTION),
            @ApiResponse(responseCode = STATUS_400, description = REGISTER_RESPONSE_400_DESCRIPTION)
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
            @ApiResponse(responseCode = STATUS_200, description = LOGIN_RESPONSE_200_DESCRIPTION),
            @ApiResponse(responseCode = STATUS_401, description = RESPONSE_401_DESCRIPTION)
    })
    public ResponseEntity<String> createAuthenticationToken(@Valid @RequestBody final LoginDTO user) {
        return ResponseEntity.ok(userService.loginUser(user));
    }

    @GetMapping("/auth/validate")
    @Operation(summary = VALIDATE_TOKEN_OPERATION_SUMMARY,
            description = VALIDATE_TOKEN_OPERATION_DESCRIPTION,
            tags = USERS_TAG)
    @ApiResponses(value = {
            @ApiResponse(responseCode = STATUS_200, description = VALIDATE_TOKEN_RESPONSE_200_DESCRIPTION),
            @ApiResponse(responseCode = STATUS_401, description = RESPONSE_401_DESCRIPTION),
    })
    public ResponseEntity<UserInfoDTO> validateToken(@RequestParam(TOKEN_PARAM) String token) {
        UserEntity validatedUser = userService.validateToken(token);
        return ResponseEntity.status(HttpStatus.OK).body(modelMapper.map(validatedUser, UserInfoDTO.class));
    }
}
