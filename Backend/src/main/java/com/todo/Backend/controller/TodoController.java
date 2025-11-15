package com.todo.Backend.controller;


import com.todo.Backend.dto.TodoDTO;
import com.todo.Backend.model.Todo;
import com.todo.Backend.service.TodoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
public class TodoController {
    private final TodoService todoService;

    public TodoController(TodoService todoService){
        this.todoService = todoService;
    }

    @PostMapping
    public ResponseEntity<TodoDTO> createTodo(@Valid @RequestBody TodoDTO todoDTO){
        TodoDTO created = todoService.createTodo(todoDTO);
        return ResponseEntity.ok(created);
    }

    @GetMapping
    public ResponseEntity<List<TodoDTO>> getAllTodos(){
        return ResponseEntity.ok(todoService.getAllTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TodoDTO> getTodoById(@PathVariable Long id){
        return ResponseEntity.ok(todoService.getTodoByID(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TodoDTO> updateTodo(@PathVariable Long id, @RequestBody TodoDTO todoDTO){
        return  ResponseEntity.ok(todoService.updateTodo(id, todoDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id){
        todoService.deleteTodo(id);
        return ResponseEntity.noContent().build();
    }
}
