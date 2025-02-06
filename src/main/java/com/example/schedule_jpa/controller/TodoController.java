package com.example.schedule_jpa.controller;

import com.example.schedule_jpa.dto.TodoRequestDto;
import com.example.schedule_jpa.dto.TodoResponseDto;
import com.example.schedule_jpa.service.TodoService;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<TodoResponseDto> save(@RequestHeader Long memberId, @RequestBody TodoRequestDto requestDto){
        TodoResponseDto responseDto
                = todoService.save(memberId, requestDto.getTitle(), requestDto.getContent());
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<TodoResponseDto>> findALL(@RequestHeader Long memberId){
        List<TodoResponseDto> responseDtoList = todoService.findAll(memberId);
        return new ResponseEntity<>(responseDtoList, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<TodoResponseDto> findById(@RequestHeader Long memberId, @PathVariable Long id){
        TodoResponseDto responseDto = todoService.findById(memberId, id);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateTodo(@RequestHeader Long memberId, @PathVariable Long id, @RequestBody TodoRequestDto requestDto){
        todoService.updateTodo(memberId, id, requestDto.getTitle(), requestDto.getContent());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@RequestHeader Long memberId, @PathVariable Long id){
        todoService.deleteTodo(memberId, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
