package com.example.schedule_jpa.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateTodoCommand {
    private Long id;
    private Long memberId;
    private String title;
    private String content;
}
