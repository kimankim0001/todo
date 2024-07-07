package com.sparta.todo.repository;

import com.sparta.todo.entity.Comment;

import java.util.List;

public interface CommentRepositoryCustom {
    List<Comment> findAllByTodoIdWithPageAndSortDesc(Long todoId, long offset, int pageSize);
}
