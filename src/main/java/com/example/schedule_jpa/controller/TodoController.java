package com.example.schedule_jpa.controller;

import com.example.schedule_jpa.dto.TodoRequestDto;
import com.example.schedule_jpa.dto.TodoResponseDto;
import com.example.schedule_jpa.service.TodoService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpRequest;
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

        TodoResponseDto responseDto
                = todoService.save(memberId, requestDto.getTitle(), requestDto.getContent());
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<TodoResponseDto>> findALL(Pageable pageable){
        List<TodoResponseDto> responseDtoList = todoService.findAll(pageable);
        return new ResponseEntity<>(responseDtoList, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<TodoResponseDto> findById(@PathVariable Long id, Pageable pageable){
        TodoResponseDto responseDto = todoService.findById(id, pageable);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateTodo(HttpServletRequest request, @PathVariable Long id, @RequestBody TodoRequestDto requestDto){
        HttpSession session = request.getSession();
        Long memberId = (Long) session.getAttribute("token");

        todoService.updateTodo(memberId, id, requestDto.getTitle(), requestDto.getContent());
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
