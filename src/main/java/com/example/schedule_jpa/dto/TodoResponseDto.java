package com.example.schedule_jpa.dto;

import com.example.schedule_jpa.entity.Todo;
import lombok.Getter;

@Getter
public class TodoResponseDto {
    private final Long id;
    private final String title;
    private final String content;
    private final String username;
    private final Long memberId;

    public TodoResponseDto(Long id, String title, String content, Long memberId, String username) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.memberId = memberId;
        this.username = username;
    }


    public static TodoResponseDto toDto(Todo todo){
        return new TodoResponseDto(todo.getId(), todo.getTitle(), todo.getContent(), todo.getMember().getId(), todo.getMember().getUsername());
    }
}
