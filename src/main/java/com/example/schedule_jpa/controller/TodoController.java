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

@RestController
@RequiredArgsConstructor
@RequestMapping("/todos")
public class TodoController {
    private final TodoService todoService;

    @PostMapping
    public ResponseEntity<TodoResponseDto> save(@SessionAttribute("token") String memberId, @Valid  @RequestBody TodoRequestDto requestDto){
        CreateTodoCommand todoCommand
                = new CreateTodoCommand(Long.parseLong(memberId), requestDto.getTitle() , requestDto.getContent());
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
    public ResponseEntity<Void> updateTodo(@SessionAttribute("token") String memberId, @PathVariable Long id, @RequestBody TodoRequestDto requestDto){
        UpdateTodoCommand todoCommand =
                new UpdateTodoCommand(id, Long.parseLong(memberId), requestDto.getTitle() , requestDto.getContent());
        todoService.updateTodo(todoCommand);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@SessionAttribute("token") String memberId, @PathVariable Long id){
        todoService.deleteTodo(Long.parseLong(memberId), id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
