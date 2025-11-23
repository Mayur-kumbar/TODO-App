package com.todo.Backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TodoNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleTodoNotFound(TodoNotFoundException todoNotFoundException){
        Map<String, String> error = new HashMap<>();
        error.put("error", todoNotFoundException.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<Map<String, String>> handleUserAlreadyExist(UserAlreadyExistException userAlreadyExistException){
        Map<String, String> error = new HashMap<>();
        error.put("error", userAlreadyExistException.getMessage());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, String >> handleUserNotFound(UserNotFoundException userNotFoundException){
        Map<String, String> error = new HashMap<>();
        error.put("error", userNotFoundException.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<Map<String, String >> handleInvalidPassword(InvalidPasswordException invalidPasswordException){
        Map<String, String> error = new HashMap<>();
        error.put("error", invalidPasswordException.getMessage());
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UnauthorizedAccessException.class)
    public ResponseEntity<Map<String, String>> handleUnauthorizedAccess(UnauthorizedAccessException unauthorizedAccessException){
        Map<String, String> error = new HashMap<>();
        error.put("error", unauthorizedAccessException.getMessage());
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

}
