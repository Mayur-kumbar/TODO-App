package com.todo.Backend.service;

import com.todo.Backend.dto.UserResponse;
import com.todo.Backend.model.User;
import com.todo.Backend.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public UserResponse getUserDetails(){

        Long id = getCurrentUserId();

        User user = userRepository.getById(id);

        return new UserResponse(user.getName(), user.getEmail());

    }

    public Long getCurrentUserId(){
        return (Long) SecurityContextHolder.getContext().getAuthentication().getDetails();
    }

}
