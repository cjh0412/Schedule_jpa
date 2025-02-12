package com.example.schedule_jpa.controller;

import com.example.schedule_jpa.command.CreateCommentCommand;
import com.example.schedule_jpa.command.UpdateCommentCommand;
import com.example.schedule_jpa.dto.CommentRequestDto;
import com.example.schedule_jpa.dto.CommentResponseDto;
import com.example.schedule_jpa.entity.Comment;
import com.example.schedule_jpa.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentResponseDto> createComment(HttpServletRequest request, @RequestBody CommentRequestDto commentRequestDto){
        HttpSession session = request.getSession();
        Long memberId = (Long) session.getAttribute("token");
        CreateCommentCommand commentCommand = new CreateCommentCommand(memberId, commentRequestDto.getContent(), commentRequestDto.getTodoId());
        CommentResponseDto commentResponseDto = commentService.save(commentCommand);
        return new ResponseEntity<>(commentResponseDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CommentResponseDto>> findAll(){
        List<CommentResponseDto> commentResponseDtoList = commentService.findAll();
        return new ResponseEntity<>(commentResponseDtoList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentResponseDto> findById(@PathVariable Long id){
        Comment comment = commentService.findById(id);

        return new ResponseEntity<>(CommentResponseDto.toDto(comment), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateComment(@PathVariable Long id, HttpServletRequest request, @RequestBody CommentRequestDto commentRequestDto){
        HttpSession session = request.getSession();
        Long memberId = (Long) session.getAttribute("token");
        UpdateCommentCommand commentCommand = new UpdateCommentCommand(id, commentRequestDto.getContent(), memberId);
        commentService.updateComment(commentCommand);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id, HttpServletRequest request){
        HttpSession session = request.getSession();
        Long memberId = (Long) session.getAttribute("token");
        commentService.deleteComment(id, memberId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
