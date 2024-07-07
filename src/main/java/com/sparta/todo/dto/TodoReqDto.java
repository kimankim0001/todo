package com.sparta.todo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TodoReqDto {

    private String title;

    private String description;

    public TodoReqDto(String title, String description) {
        this.title = title;
        this.description = description;
    }
}
