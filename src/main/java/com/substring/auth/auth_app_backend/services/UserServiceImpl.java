package com.substring.auth.auth_app_backend.services;


import com.substring.auth.auth_app_backend.dtos.UserDto;
import com.substring.auth.auth_app_backend.entities.Provider;
import com.substring.auth.auth_app_backend.entities.User;
import com.substring.auth.auth_app_backend.exceptions.ResourceNotFoundException;
import com.substring.auth.auth_app_backend.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public UserDto createUser(UserDto userDto) {

        if(userDto.getEmail() == null || userDto.getEmail().isBlank()){
            throw new IllegalArgumentException("Email is required");
        }

        if(userRepository.existsByEmail(userDto.getEmail())){
            throw new IllegalArgumentException("Email already exists");
        }


        User user = modelMapper.map(userDto, User.class);

        user.setProvider(userDto.getProvider() != null ? userDto.getProvider() : Provider.LOCAL);

        //TODO: role will assign here to user -- for authorization we will do later...


       User savedUser = userRepository.save(user);


        return modelMapper.map(savedUser , UserDto.class);

        
    }

    @Override
    public UserDto getUserByEmail(String email) {

      User user =  userRepository
               .findByEmail(email)
               .orElseThrow(()-> new ResourceNotFoundException("Resource not found with given email id."));

       return modelMapper.map( user, UserDto.class);
    }

    @Override
    public UserDto updateUser(UserDto userDto, String UserId) {
        return null;
    }

    @Override
    public UserDto deleteUser(String userId) {
        return null;
    }

    @Override
    public UserDto getUserById(String userId) {
        return null;
    }

    @Override
    @Transactional
    public Iterable<UserDto> getAllUsers() {
        return userRepository
                .findAll()
                .stream()
                .map(( User user) -> modelMapper.map(user, UserDto.class))
                .toList();


    }
}
