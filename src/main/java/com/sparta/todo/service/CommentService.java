package com.sparta.todo.service;

import com.sparta.todo.dto.CommentReqDto;
import com.sparta.todo.dto.CommentResDto;
import com.sparta.todo.entity.Comment;
import com.sparta.todo.entity.Todo;
import com.sparta.todo.entity.User;
import com.sparta.todo.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final TodoService todoService;
    private final MessageSource messageSource;

    @Transactional
    public CommentResDto createComment(long todoId, User user, CommentReqDto commentReqDto) {
        Todo todo = todoService.findTodo(todoId);
        Comment comment = commentRepository.save(new Comment(commentReqDto.getContent(), user, todo));
        return new CommentResDto(comment.getId(), comment.getContent(), comment.getUser().getUsername(), comment.getTodo().getId(), comment.getCreatedAt());
    }

    @Transactional(readOnly = true)
    public List<CommentResDto> findAllByTodoIdWithPageAndSortDesc(long todoId, int page, int pageSize) {
        PageRequest pageRequest = PageRequest.of(page - 1, pageSize);
        return commentRepository.findAllByTodoIdWithPageAndSortDesc(todoId, pageRequest.getOffset(), pageRequest.getPageSize())
                .stream()
                .map(comment ->
                        CommentResDto.builder()
                                .commentId(comment.getId())
                                .content(comment.getContent())
                                .username(comment.getUser().getUsername())
                                .todoId(comment.getTodo().getId())
                                .createdAt(comment.getCreatedAt())
                                .build())
                .collect(Collectors.toList());
    }

    @Transactional
    public CommentResDto updateComment(long todoId, long commentId, CommentReqDto commentReqDto, User user) {
        todoService.findTodo(todoId);
        Comment comment = findComment(commentId);
        String loginUsername = user.getUsername();
        String commentUsername = comment.getUser().getUsername();

        if (!loginUsername.equals(commentUsername)) {
            throw new IllegalArgumentException(messageSource.getMessage(
                    "author.and.user.mismatch",
                    null,
                    "User Mismatch",
                    Locale.getDefault()
            ));
        }

        comment.updateComment(commentReqDto.getContent());

        return new CommentResDto(comment.getId(), comment.getContent(), comment.getUser().getUsername(), comment.getTodo().getId(), comment.getCreatedAt());
    }

    @Transactional
    public void deleteComment(long todoId, long commentId, User user) {

        todoService.findTodo(todoId);
        Comment comment = findComment(commentId);
        String loginUsername = user.getUsername();
        String commentUsername = comment.getUser().getUsername();

        if (!loginUsername.equals(commentUsername)) {
            throw new IllegalArgumentException(messageSource.getMessage(
                    "author.and.user.mismatch",
                    null,
                    "User Mismatch",
                    Locale.getDefault()
            ));
        }
        commentRepository.delete(comment);
    }

    public Comment findComment(long commentId) {
        return commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalArgumentException(messageSource.getMessage(
                        "not.found.comment",
                        null,
                        "Not Found Comment",
                        Locale.getDefault()
                )));
    }
}
