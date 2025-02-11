package com.example.schedule_jpa.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateCommentCommand {
    private  Long id;
    private String content;
    private Long memberId;
}
