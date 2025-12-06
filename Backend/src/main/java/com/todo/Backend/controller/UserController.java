package com.todo.Backend.controller;

import com.todo.Backend.dto.UserResponse;
import com.todo.Backend.repository.UserRepository;
import com.todo.Backend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<UserResponse> getUserDetails(){

        return ResponseEntity.ok(userService.getUserDetails());
    }

}
