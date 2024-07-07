package com.sparta.todo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentReqDto {

    private String content;

    public CommentReqDto(String content) {
        this.content = content;
    }
}
