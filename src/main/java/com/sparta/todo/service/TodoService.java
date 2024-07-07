package com.sparta.todo.service;

import com.sparta.todo.dto.TodoReqDto;
import com.sparta.todo.dto.TodoResDto;
import com.sparta.todo.entity.Todo;
import com.sparta.todo.entity.User;
import com.sparta.todo.repository.TodoRepository;
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
public class TodoService {

    private final TodoRepository todoRepository;
    private final MessageSource messageSource;

    @Transactional
    public TodoResDto createTodo(User user, TodoReqDto todoReqDto) {
        Todo todo = todoRepository.save(new Todo(todoReqDto.getTitle(), todoReqDto.getDescription(), user));
        return new TodoResDto(todo.getId(), todo.getTitle(), todo.getDescription(), todo.getUser().getUsername(), todo.getCreatedAt());
    }

    @Transactional(readOnly = true)
    public TodoResDto getTodo(long todoId) {
        Todo todo = findTodo(todoId);
        return new TodoResDto(todo.getId(), todo.getTitle(), todo.getDescription(), todo.getUser().getUsername(), todo.getCreatedAt());
    }

    @Transactional(readOnly = true)
    public List<TodoResDto> findAllByTodoWithPageAndSortDesc(int page, int pageSize) {
        PageRequest pageRequest = PageRequest.of(page - 1, pageSize);
        return todoRepository.findAllByTodoWithPageAndSortDesc(pageRequest.getOffset(), pageRequest.getPageSize())
                .stream()
                .map(todo ->
                        TodoResDto.builder()
                                .todoId(todo.getId())
                                .title(todo.getTitle())
                                .description(todo.getDescription())
                                .username(todo.getUser().getUsername())
                                .createdAt(todo.getCreatedAt())
                                .build())
                .collect(Collectors.toList());
    }

    public Todo findTodo(long todoId) {
        return todoRepository.findById(todoId).orElseThrow(
                () -> new IllegalArgumentException(messageSource.getMessage(
                        "not.found.todo",
                        null,
                        "Not Found Todo",
                        Locale.getDefault()
                )));
    }
}
