package com.example.schedule_jpa.exception;

import com.example.schedule_jpa.exception.errorcode.CommentErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
public class CommentException extends RuntimeException{
    private final CommentErrorCode errorCode;

    public CommentException(CommentErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    @ToString
    @Getter
    @AllArgsConstructor
    public static class ExceptionResponse{
        private CommentErrorCode errorCode;
        private String message;
    }
}
