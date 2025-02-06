package com.example.schedule_jpa.service;

import com.example.schedule_jpa.dto.TodoResponseDto;
import com.example.schedule_jpa.entity.Todo;
import com.example.schedule_jpa.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;

    public TodoResponseDto save(String title, String content, String author){

        Todo todo = new Todo(title, content, author);
        todoRepository.save(todo);
        return new TodoResponseDto(todo.getId(), todo.getTitle(), todo.getContent(), todo.getAuthor());
    }

    public List<TodoResponseDto> findAll() {
        return  todoRepository.findAll()
                .stream()
                .map(TodoResponseDto :: toDto)
                .toList();
    }

    public TodoResponseDto findById(Long id) {
        Todo todo = todoRepository.findByIdOrElseThrow(id);
        return new TodoResponseDto(todo.getId(), todo.getTitle(), todo.getContent(), todo.getAuthor());
    }

    @Transactional
    public void updateTodo(Long id, String title, String content, String author) {
        Todo todo = todoRepository.findByIdOrElseThrow(id);

        if(!id.equals(todo.getId())){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "수정할 수 있는 데이터가 없습니다.");
        }

        todo.updateTodo(title, content, author);

    }

    public void deleteTodo(Long id) {
        Todo todo = todoRepository.findByIdOrElseThrow(id);

        if(!id.equals(todo.getId())){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "삭제할 수 있는 데이터가 없습니다.");
        }

        todoRepository.deleteById(id);
    }
}
