package com.example.schedule_jpa.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateMemberCommand {
    private  String username;
    private String email;
    private String password;
}
