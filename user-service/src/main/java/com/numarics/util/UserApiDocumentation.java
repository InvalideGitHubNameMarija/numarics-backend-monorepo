package com.numarics.util;

public class UserApiDocumentation {
    public static final String USERS_TAG = "Users";
    public static final String TOKEN_PARAM = "token";

    public static final String STATUS_200 = "200";
    public static final String STATUS_201 = "201";
    public static final String STATUS_400 = "400";
    public static final String STATUS_401 = "401";

    public static final String REGISTER_OPERATION_SUMMARY = "Register new user";
    public static final String REGISTER_OPERATION_DESCRIPTION =
            "This endpoint allows for registering a new user by providing user details.";
    public static final String REGISTER_RESPONSE_201_DESCRIPTION = "User successfully registered";
    public static final String REGISTER_RESPONSE_400_DESCRIPTION = "Validation error occurred during registration";

    public static final String LOGIN_OPERATION_SUMMARY = "Login user";
    public static final String LOGIN_OPERATION_DESCRIPTION =
            "This endpoint allows users to log in by providing their credentials. Upon successful authentication, a token is returned.";
    public static final String LOGIN_RESPONSE_200_DESCRIPTION = "User successfully authenticated and token returned";

    public static final String VALIDATE_TOKEN_OPERATION_SUMMARY = "Validate user token";
    public static final String VALIDATE_TOKEN_OPERATION_DESCRIPTION =
            "This endpoint validates the provided JWT token and returns user information if the token is valid.";
    public static final String VALIDATE_TOKEN_RESPONSE_200_DESCRIPTION = "Token is valid, user information returned";

    public static final String RESPONSE_401_DESCRIPTION = "Unauthorized: User not authenticated";
}
