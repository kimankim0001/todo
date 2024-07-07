package com.sparta.todo.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class TodoResDto {

    private Long todoId;

    private String title;

    private String description;

    private String username;

    private LocalDateTime createdAt;

    @Builder
    public TodoResDto(Long todoId, String title, String description, String username, LocalDateTime createdAt) {
        this.todoId = todoId;
        this.title = title;
        this.description = description;
        this.username = username;
        this.createdAt = createdAt;
    }
}
