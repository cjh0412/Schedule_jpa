package com.example.schedule_jpa.dto;

import lombok.Getter;

@Getter
public class TodoRequestDto {

    private String title;

    private String content;

    private Long memberId;

    public TodoRequestDto(String title, String content, Long memberId) {
        this.title = title;
        this.content = content;
        this.memberId = memberId;
    }
}
