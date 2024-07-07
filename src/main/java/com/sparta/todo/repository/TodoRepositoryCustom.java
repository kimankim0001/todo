package com.sparta.todo.repository;

import com.sparta.todo.entity.Todo;

import java.util.List;

public interface TodoRepositoryCustom {
    List<Todo> findAllByTodoWithPageAndSortDesc(long offset, int pageSize);
}
