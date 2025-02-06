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
    public ResponseEntity<TodoResponseDto> save(@RequestBody TodoRequestDto requestDto){
        TodoResponseDto responseDto
                = todoService.save(requestDto.getTitle(), requestDto.getContent(), requestDto.getAuthor());
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<TodoResponseDto>> findALL(){
        List<TodoResponseDto> responseDtoList = todoService.findAll();
        return new ResponseEntity<>(responseDtoList, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<TodoResponseDto> findById(@PathVariable Long id){
        TodoResponseDto responseDto = todoService.findById(id);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateTodo(@PathVariable Long id, @RequestBody TodoRequestDto requestDto){
        todoService.updateTodo(id, requestDto.getTitle(), requestDto.getContent(), requestDto.getAuthor());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id){
        todoService.deleteTodo(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
