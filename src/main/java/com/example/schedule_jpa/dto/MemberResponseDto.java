package com.example.schedule_jpa.dto;

import com.example.schedule_jpa.entity.Member;
import lombok.Getter;

@Getter
public class MemberResponseDto {

    private Long id;
    private String email;
    private String username;

    public MemberResponseDto(Long id, String email, String username) {
        this.id = id;
        this.email = email;
        this.username = username;
    }

    public static MemberResponseDto toDto(Member member){
        return new MemberResponseDto(member.getId(), member.getUsername(), member.getEmail());
    }
}
