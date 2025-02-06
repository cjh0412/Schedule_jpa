package com.example.schedule_jpa.dto;

import lombok.Getter;

@Getter
public class TodoRequestDto {

    private String title;

    private String content;

    private String author;

    public TodoRequestDto(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }
}
