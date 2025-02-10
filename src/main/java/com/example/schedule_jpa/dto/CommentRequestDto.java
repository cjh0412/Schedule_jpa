package com.example.schedule_jpa.dto;

import lombok.Getter;

@Getter
public class CommentRequestDto {
    private  Long todoId;
    private String content;
}
