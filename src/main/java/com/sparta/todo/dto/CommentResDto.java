package com.sparta.todo.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResDto {

    private Long commentId;

    private String content;

    private String username;

    private Long todoId;

    private LocalDateTime createdAt;

    @Builder
    public CommentResDto(Long commentId, String content, String username, Long todoId, LocalDateTime createdAt) {
        this.commentId = commentId;
        this.content = content;
        this.username = username;
        this.todoId = todoId;
        this.createdAt = createdAt;
    }
}
