package com.todo.Backend.service.implementation;

import com.todo.Backend.dto.TodoDTO;
import com.todo.Backend.model.Todo;
import com.todo.Backend.repository.TodoRepository;
import com.todo.Backend.service.TodoService;
import jakarta.validation.constraints.Null;
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
        return dto;
    }

    private Todo mapToEntity(TodoDTO todoDTO){
        Todo todo = new Todo();
        todo.setId(todoDTO.getId());
        todo.setTitle(todoDTO.getTitle());
        todo.setDescription(todoDTO.getDescription());
        todo.setCompleted(todoDTO.isCompleted());
        return todo;
    }

    @Override
    public TodoDTO createTodo(TodoDTO todoDTO){
        Todo todo = mapToEntity(todoDTO);
        Todo saved = todoRepository.save(todo);
        return mapToDTO(saved);
    }

    @Override
    public List<TodoDTO> getAllTodos() {
        return todoRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TodoDTO getTodoByID(Long id) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Todo not found"));
        return mapToDTO(todo);
    }

    @Override
    public TodoDTO updateTodo(Long id, TodoDTO todoDTO) {
        Todo existing = todoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Todo not found"));

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
        Todo existing = todoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Todo not found"));
        todoRepository.delete(existing);
    }
}
