package com.numarics.service;

import com.numarics.dto.LoginDTO;
import com.numarics.dto.RegisterUserDTO;
import com.numarics.model.UserEntity;

public interface UserService {

    UserEntity registerUser(RegisterUserDTO userDTO);

    String loginUser(LoginDTO loginDTO);

}
