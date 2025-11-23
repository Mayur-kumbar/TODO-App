package com.todo.Backend.service.implementation;

import com.todo.Backend.dto.TodoDTO;
import com.todo.Backend.exception.TodoNotFoundException;
import com.todo.Backend.exception.UnauthorizedAccessException;
import com.todo.Backend.model.Todo;
import com.todo.Backend.repository.TodoRepository;
import com.todo.Backend.service.TodoService;
import jakarta.validation.constraints.Null;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TodoServiceImplementation implements TodoService {
    private final TodoRepository todoRepository;

    public TodoServiceImplementation(TodoRepository todoRepository){
        this.todoRepository = todoRepository;
    }

    private TodoDTO mapToDTO(Todo todo){
        TodoDTO dto = new TodoDTO();
        dto.setId(todo.getId());
        dto.setTitle(todo.getTitle());
        dto.setDescription(todo.getDescription());
        dto.setCompleted(todo.isCompleted());
        dto.setUserId(todo.getUserId());
        return dto;
    }

    private Todo mapToEntity(TodoDTO todoDTO){
        Todo todo = new Todo();
        todo.setId(todoDTO.getId());
        todo.setTitle(todoDTO.getTitle());
        todo.setDescription(todoDTO.getDescription());
        todo.setCompleted(todoDTO.isCompleted());
        todo.setUserId(todoDTO.getUserId());
        return todo;
    }

    @Override
    public TodoDTO createTodo(TodoDTO todoDTO){
        Long userId = getCurrentUserId();

        Todo todo = new Todo();
        todo.setTitle(todoDTO.getTitle());
        todo.setDescription(todoDTO.getDescription());
        todo.setCompleted(false);
        todo.setUserId(userId);

        Todo saved = todoRepository.save(todo);
        return mapToDTO(saved);
    }

    @Override
    public List<TodoDTO> getAllTodos() {
        Long userId = getCurrentUserId();
        return todoRepository.findByUserId(userId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TodoDTO getTodoByID(Long id) {
        Long userId = getCurrentUserId();
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new TodoNotFoundException(id));

        if(!todo.getUserId().equals(userId)){
            throw new UnauthorizedAccessException("Unauthorized to access this todo");
        }
        return mapToDTO(todo);
    }

    @Override
    public TodoDTO updateTodo(Long id, TodoDTO todoDTO) {
        Long userId = getCurrentUserId();
        Todo existing = todoRepository.findById(id)
                .orElseThrow(() -> new TodoNotFoundException(id));

        if(!existing.getUserId().equals(userId)){
            throw new UnauthorizedAccessException("Unauthorized to update this todo");
        }

        if(todoDTO.getTitle() != null){
            existing.setTitle(todoDTO.getTitle());
        }

        if(todoDTO.getDescription() != null){
            existing.setDescription(todoDTO.getDescription());
        }

        if(todoDTO.isCompleted() != existing.isCompleted()){
            existing.setCompleted(todoDTO.isCompleted());
        }

        Todo updated = todoRepository.save(existing);
        return mapToDTO(updated);
    }

    @Override
    public void deleteTodo(Long id) {
        Long userId = getCurrentUserId();
        Todo existing = todoRepository.findById(id)
                .orElseThrow(() -> new TodoNotFoundException(id));

        if(!existing.getUserId().equals(userId)){
            throw new UnauthorizedAccessException("Unauthorized to delete this todo");
        }
        todoRepository.delete(existing);
    }

    public Long getCurrentUserId(){
        return (Long) SecurityContextHolder.getContext().getAuthentication().getDetails();
    }
}
