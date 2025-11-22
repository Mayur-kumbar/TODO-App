package com.todo.Backend.service;

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


    public void registerUser(RegisterDTO request){
        Optional<User> existingUser = userRepository.findByEmail(request.getEmail());
        if(existingUser.isPresent()){
            throw new UserAlreadyExistException(request.getEmail());
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(user);
    }

    public String loginUser(LoginDTO request){
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new InvalidPasswordException("Invalid password");
        }

        return jwtutil.generateToken(user.getEmail());
    }


}
