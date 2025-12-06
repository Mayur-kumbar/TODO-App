package com.todo.Backend.service;

import com.todo.Backend.dto.AuthResponse;
import com.todo.Backend.dto.LoginDTO;
import com.todo.Backend.dto.RegisterDTO;
import com.todo.Backend.exception.InvalidPasswordException;
import com.todo.Backend.exception.UserAlreadyExistException;
import com.todo.Backend.exception.UserNotFoundException;
import com.todo.Backend.model.User;
import com.todo.Backend.repository.UserRepository;
import com.todo.Backend.security.Jwtutil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final Jwtutil jwtutil;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, Jwtutil jwtutil){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtutil = jwtutil;
    }


    public AuthResponse registerUser(RegisterDTO request){
        Optional<User> existingUser = userRepository.findByEmail(request.getEmail());
        if(existingUser.isPresent()){
            throw new UserAlreadyExistException(request.getEmail());
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setName(request.getName());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        User saved = userRepository.save(user);

        String token = jwtutil.generateToken(saved);

        return new AuthResponse(token, saved.getId(), saved.getName(), saved.getEmail());

    }

    public AuthResponse loginUser(LoginDTO request){
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new InvalidPasswordException("Invalid password");
        }

        String token = jwtutil.generateToken(user);
        return new AuthResponse(token, user.getId(), user.getName(), user.getEmail());
    }


}
