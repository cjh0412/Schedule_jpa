package com.example.schedule_jpa.exception;

import com.example.schedule_jpa.exception.errorcode.TodoErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
public class TodoException extends RuntimeException{
    private final TodoErrorCode errorCode;

    public TodoException(TodoErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    @ToString
    @Getter
    @AllArgsConstructor
    public static class ExceptionResponse{
        private TodoErrorCode errorCode;
        private String message;
    }
}
