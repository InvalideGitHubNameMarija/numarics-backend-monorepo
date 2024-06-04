package com.numarics.service;

import com.numarics.dto.LoginDTO;
import com.numarics.dto.RegisterUserDTO;
import com.numarics.enums.Role;
import com.numarics.model.UserEntity;
import java.util.Optional;

public interface UserService {

    UserEntity registerUser(RegisterUserDTO userDTO, Optional<Role> role);

    String loginUser(LoginDTO loginDTO);

     UserEntity validateToken(String token);
}
