package com.example.schedule_jpa.dto;

import com.example.schedule_jpa.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {
    private final Long id;
    private final Long todoId;
    private final String content;
    private final Long memberId;
    private final String username;
    private final LocalDateTime createAt;

    public CommentResponseDto(Long id, Long todoId, String content, Long memberId, String username, LocalDateTime createAt) {
        this.id = id;
        this.todoId = todoId;
        this.content = content;
        this.memberId = memberId;
        this.username = username;
        this.createAt = createAt;
    }

    public static CommentResponseDto toDto(Comment comment){
        return new CommentResponseDto(
                comment.getId(),
                comment.getTodo().getId(),
                comment.getContent(),
                comment.getMember().getId(),
                comment.getMember().getUsername(),
                comment.getCreatedAt());
    }
}
