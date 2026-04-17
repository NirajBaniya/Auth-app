package com.substring.auth.auth_app_backend.services;

import com.substring.auth.auth_app_backend.dtos.UserDto;
import com.substring.auth.auth_app_backend.entities.User;

public interface AuthService {
    UserDto registerUser(UserDto userDto);

    //login user

}
