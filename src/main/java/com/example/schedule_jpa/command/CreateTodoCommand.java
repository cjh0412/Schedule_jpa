package com.example.schedule_jpa.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateTodoCommand {
    private Long memberId;
    private String title;
    private String content;
}
