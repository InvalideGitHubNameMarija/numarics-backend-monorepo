package com.numarics.client;

import com.numarics.dto.UserInfoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "user-service")
public interface UserServiceClient {

    @GetMapping("/api/v1/users/auth/validate")
    ResponseEntity<UserInfoDTO> validateToken(@RequestParam("token") String token);
}
