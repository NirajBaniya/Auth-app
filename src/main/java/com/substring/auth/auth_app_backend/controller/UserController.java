package com.substring.auth.auth_app_backend.controller;


import com.substring.auth.auth_app_backend.dtos.UserDto;
import com.substring.auth.auth_app_backend.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users") //api and v1 means this thing will be common. only users change
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // create user api
    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(userDto));

    }

    // get all user api
    @GetMapping
    public ResponseEntity<Iterable<UserDto>> getAllUser() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // get user by email
    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable("email") String email) {
        return ResponseEntity.ok(userService.getUserByEmail(email));
      }

      // delete user
    // api/v1/users/{user_id}
    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable("userId") String userId) {

        userService.deleteUser(userId);
    }

    //update user
    //api/v1/users/{"UserId"}
    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto, @PathVariable("userId") String userId) {
        return ResponseEntity.ok(userService.updateUser(userDto, userId));
    }

    //get user by id
    //api/v1/users/{userId}
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("userId") String userId)  {
        return ResponseEntity.ok(userService.getUserById(userId));
    }



}
