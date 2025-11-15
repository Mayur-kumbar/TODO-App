package com.todo.Backend.service;

import com.todo.Backend.dto.TodoDTO;

import java.util.List;

public interface TodoService {
    TodoDTO createTodo(TodoDTO todoDTO);

    List<TodoDTO> getAllTodos();

    TodoDTO getTodoByID(Long id);

    TodoDTO updateTodo(Long id, TodoDTO todoDTO);

    void deleteTodo(Long id);
}
