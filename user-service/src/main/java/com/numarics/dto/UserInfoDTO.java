package com.numarics.dto;

import com.numarics.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDTO {

    private Long id;
    private Role role;
}
