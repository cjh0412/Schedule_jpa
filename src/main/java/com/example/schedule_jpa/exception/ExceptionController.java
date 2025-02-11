package com.example.schedule_jpa.exception;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(TodoException.class)
    public ResponseEntity<?> handleTodoException(final TodoException e){
        return ResponseEntity.status(e.getErrorCode().getHttpStatus())
                .body(new TodoException.ExceptionResponse(e.getErrorCode(), e.getMessage()));
    }

    @ExceptionHandler(MemberException.class)
    public ResponseEntity<MemberException.ExceptionResponse> handleTodoException(final MemberException e){
        return ResponseEntity.status(e.getErrorCode().getHttpStatus())
                .body(new MemberException.ExceptionResponse(e.getErrorCode(), e.getMessage()));
    }

    @ExceptionHandler(CommentException.class)
    public ResponseEntity<CommentException.ExceptionResponse> handleTodoException(final CommentException e){
        return ResponseEntity.status(e.getErrorCode().getHttpStatus())
                .body(new CommentException.ExceptionResponse(e.getErrorCode(), e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationException(MethodArgumentNotValidException e){
        List<String> errors = e.getBindingResult()
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();

        return ResponseEntity.badRequest().body(errors);
    }
}
