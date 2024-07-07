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

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/todos/{todoId}/comments")
    public ResponseEntity<CommonResDto<CommentResDto>> createComment(
            @PathVariable(name = "todoId") long todoId, @Valid @RequestBody CommentReqDto commentReqDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        CommentResDto resDto = commentService.createComment(todoId, userDetails.getUser(), commentReqDto);
        return ResponseEntity.ok().body(new CommonResDto<>(HttpStatus.OK.value(), "댓글 작성에 성공하였습니다!", resDto));
    }

    @GetMapping("/todos/{todoId}/comments")
    public ResponseEntity<CommonResDto<List<CommentResDto>>> findAllByTodoIdWithPageAndSortDesc(
            @PathVariable(name = "todoId") long todoId, @RequestParam("page") int page) {
        List<CommentResDto> commentResDtos = commentService.findAllByTodoIdWithPageAndSortDesc(todoId, page, 5);
        return ResponseEntity.ok().body(new CommonResDto<>(HttpStatus.OK.value(), "전체 댓글 조회에 성공하였습니다!", commentResDtos));
    }

    @PatchMapping("/todos/{todoId}/comments/{commentId}")
    public ResponseEntity<CommonResDto<CommentResDto>> updateComment(
            @PathVariable(name = "todoId") long todoId, @PathVariable(name = "commentId") long commentId, @Valid @RequestBody CommentReqDto commentReqDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        CommentResDto resDto = commentService.updateComment(todoId, commentId, commentReqDto, userDetails.getUser());
        return ResponseEntity.ok().body(new CommonResDto<>(HttpStatus.OK.value(), "댓글 수정에 성공하였습니다!", resDto));
    }

    @DeleteMapping("/todos/{todoId}/comments/{commentId}")
    public ResponseEntity<CommonResDto<Void>> deleteComment(@PathVariable(name = "todoId") long todoId, @PathVariable(name = "commentId") long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        commentService.deleteComment(todoId, commentId, userDetails.getUser());
        return ResponseEntity.ok().body(new CommonResDto<>(HttpStatus.OK.value(), "댓글 삭제에 성공하였습니다!", null));
    }

}
