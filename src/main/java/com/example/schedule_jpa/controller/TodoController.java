package com.example.schedule_jpa.controller;

import com.example.schedule_jpa.command.CreateTodoCommand;
import com.example.schedule_jpa.command.UpdateTodoCommand;
import com.example.schedule_jpa.dto.TodoRequestDto;
import com.example.schedule_jpa.dto.TodoResponseDto;
import com.example.schedule_jpa.entity.Todo;
import com.example.schedule_jpa.service.TodoService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/todos")
public class TodoController {
    private final TodoService todoService;

    @PostMapping
    public ResponseEntity<TodoResponseDto> save(HttpServletRequest request, @Valid  @RequestBody TodoRequestDto requestDto){
        HttpSession session = request.getSession();
        Long memberId = (Long) session.getAttribute("token");
        CreateTodoCommand todoCommand
                = new CreateTodoCommand(memberId, requestDto.getTitle() , requestDto.getContent());
        TodoResponseDto responseDto
                = todoService.save(todoCommand);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<TodoResponseDto>> findALL(Pageable pageable){
        Page<TodoResponseDto> responseDtoPage = todoService.findAll(pageable);
        return new ResponseEntity<>(responseDtoPage, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TodoResponseDto> findById(@PathVariable Long id){
        Todo todo = todoService.findById(id);
        return new ResponseEntity<>(TodoResponseDto.toDto(todo), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateTodo(HttpServletRequest request, @PathVariable Long id, @RequestBody TodoRequestDto requestDto){
        HttpSession session = request.getSession();
        Long memberId = (Long) session.getAttribute("token");

        UpdateTodoCommand todoCommand =
                new UpdateTodoCommand(id, memberId, requestDto.getTitle() , requestDto.getContent());
        todoService.updateTodo(todoCommand);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(HttpServletRequest request, @PathVariable Long id){
        HttpSession session = request.getSession();
        Long memberId = (Long) session.getAttribute("token");

        todoService.deleteTodo(memberId, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
