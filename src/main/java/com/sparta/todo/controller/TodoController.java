package com.sparta.todo.controller;

import com.sparta.todo.dto.CommonResDto;
import com.sparta.todo.dto.TodoReqDto;
import com.sparta.todo.dto.TodoResDto;
import com.sparta.todo.security.UserDetailsImpl;
import com.sparta.todo.service.TodoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    @PostMapping("/todos")
    public ResponseEntity<CommonResDto<TodoResDto>> createTodo (
            @Valid @RequestBody TodoReqDto todoReqDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        TodoResDto todoResDto = todoService.createTodo(userDetails.getUser(), todoReqDto);
        return ResponseEntity.ok().body(new CommonResDto<>(HttpStatus.OK.value(), "일정 작성에 성공하였습니다!", todoResDto));
    }

    @GetMapping("/todos/{todoId}")
    public ResponseEntity<CommonResDto<TodoResDto>> getTodo (
            @PathVariable(name = "todoId") long todoId) {
        TodoResDto todoResDto = todoService.getTodo(todoId);
        return ResponseEntity.ok().body(new CommonResDto<>(HttpStatus.OK.value(), "ID와 일치하는 일정 조회에 성공하였습니다!", todoResDto));
    }

    @GetMapping("/todos")
    public ResponseEntity<CommonResDto<List<TodoResDto>>> findAllByTodoWithPageAndSortDesc (
            @RequestParam("page") int page) {
        List<TodoResDto> todoResDtos = todoService.findAllByTodoWithPageAndSortDesc(page,5);
        return ResponseEntity.ok().body(new CommonResDto<>(HttpStatus.OK.value(), "전체 일정 조회에 성공하였습니다!", todoResDtos));
    }
}
