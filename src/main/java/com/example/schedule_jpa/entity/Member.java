package com.example.schedule_jpa.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Entity
@Getter
@Table(name = "member")
public class Member extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Size(max = 4, message = "사용자명은 4자 이하로 입력해주세요.")
    private String username;

    @Column(nullable = false)
    @Pattern(regexp = "^[\\w!#$%&'*+/=?`{|}~^.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$", message = "이메일 형식이 올바르지 않습니다.")
    private String email;

    @Column(nullable = false)
    private String password;

    public Member(){

    }

    public Member(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
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
