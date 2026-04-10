package com.substring.auth.auth_app_backend.services;

import com.substring.auth.auth_app_backend.dtos.UserDto;
import com.substring.auth.auth_app_backend.entities.User;

public interface UserService {

    //Create User
    UserDto createUser(UserDto userDto);

    //get user by email
    UserDto getUserByEmail(String Email);

    //update user
    UserDto updateUser(UserDto userDto, String UserId);

    //delete User
    UserDto deleteUser(String userId);

    //get user by id
    UserDto getUserById(String userId);

    //get all users
    Iterable<UserDto> getAllUsers();
}
