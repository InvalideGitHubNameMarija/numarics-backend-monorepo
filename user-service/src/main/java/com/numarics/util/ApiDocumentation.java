package com.numarics.util;

public interface ApiDocumentation {
    String USERS_TAG = "Users";

    String REGISTER_OPERATION_SUMMARY = "Register new user";
    String REGISTER_OPERATION_DESCRIPTION = "This endpoint allows for registering a new user by providing user details.";
    String REGISTER_RESPONSE_201_DESCRIPTION = "User successfully registered";
    String REGISTER_RESPONSE_400_DESCRIPTION = "Validation error occurred during registration";

    String LOGIN_OPERATION_SUMMARY = "Login user";
    String LOGIN_OPERATION_DESCRIPTION = "This endpoint allows users to log in by providing their credentials. Upon successful authentication, a token is returned.";
    String LOGIN_RESPONSE_200_DESCRIPTION = "User successfully authenticated and token returned";
    String LOGIN_RESPONSE_401_DESCRIPTION = "Invalid credentials provided";
}
