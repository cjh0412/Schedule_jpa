package com.example.schedule_jpa.command;

import com.example.schedule_jpa.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateCommentCommand {
    private  Long todoId;
    private String content;
    private Long memberId;
}
