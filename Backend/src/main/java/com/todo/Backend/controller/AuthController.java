package com.todo.Backend.controller;

import com.todo.Backend.dto.AuthResponse;
import com.todo.Backend.dto.LoginDTO;
import com.todo.Backend.dto.RegisterDTO;
import com.todo.Backend.model.User;
import com.todo.Backend.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterDTO request){
        AuthResponse res = authService.registerUser(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginDTO request){
        return ResponseEntity.ok( authService.loginUser(request));
    }
}
