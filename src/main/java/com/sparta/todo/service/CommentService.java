package com.sparta.todo.service;

import com.sparta.todo.dto.CommentReqDto;
import com.sparta.todo.dto.CommentResDto;
import com.sparta.todo.entity.Comment;
import com.sparta.todo.entity.Todo;
import com.sparta.todo.entity.User;
import com.sparta.todo.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final TodoService todoService;

    @Transactional
    public CommentResDto createComment(long todoId, User user, CommentReqDto commentReqDto) {
        Todo todo = todoService.findTodo(todoId);
        Comment comment = commentRepository.save(new Comment(commentReqDto.getContent(), user, todo));
        return new CommentResDto(comment.getId(),comment.getContent(),comment.getUser().getUsername(),comment.getTodo().getId(),comment.getCreatedAt());

    }
}
