package com.todo.Backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TodoDTO {
    private Long id;

    @NotBlank(message = "Title cannot be empty")
    private String title;

    private String description;
    private boolean completed;
}
