package com.example.schedule_jpa.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateMemberCommand {
    private  Long id;
    private String username;
    private String password;
    private String email;

}
