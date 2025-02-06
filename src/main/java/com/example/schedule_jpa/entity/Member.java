package com.example.schedule_jpa.entity;


import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "member")
public class Member extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String email;

    public Member(){

    }

    public Member(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public void updateMember(String username, String email) {
        this.username = username;
        this.email = email;
    }
}
