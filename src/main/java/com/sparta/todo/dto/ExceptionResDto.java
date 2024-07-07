package com.sparta.todo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ExceptionResDto {

    private int status;

    private String message;

    public ExceptionResDto(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
