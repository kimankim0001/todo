package com.sparta.todo.controller;

import com.sparta.todo.dto.CommentReqDto;
import com.sparta.todo.dto.CommentResDto;
import com.sparta.todo.dto.CommonResDto;
import com.sparta.todo.security.UserDetailsImpl;
import com.sparta.todo.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/todos/{todoId}/comments")
    public ResponseEntity<CommonResDto<CommentResDto>> createComment (
            @PathVariable(name = "todoId") long todoId, @Valid @RequestBody CommentReqDto commentReqDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        CommentResDto resDto = commentService.createComment(todoId, userDetails.getUser(), commentReqDto);
        return ResponseEntity.ok().body(new CommonResDto<>(HttpStatus.OK.value(), "댓글 작성에 성공하였습니다!", resDto));
    }
}
