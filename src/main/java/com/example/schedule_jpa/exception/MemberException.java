package com.example.schedule_jpa.exception;

import com.example.schedule_jpa.exception.errorcode.MemberErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
public class MemberException extends RuntimeException{
    private final MemberErrorCode errorCode;

    public MemberException(MemberErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    @ToString
    @Getter
    @AllArgsConstructor
    public static class ExceptionResponse{
        private MemberErrorCode errorCode;
        private String message;
    }
}
