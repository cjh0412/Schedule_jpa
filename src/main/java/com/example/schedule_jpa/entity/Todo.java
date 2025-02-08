package com.example.schedule_jpa.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import org.apache.tomcat.util.http.parser.Authorization;

@Entity
@Getter
@Table(name = "todo")
public class Todo extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Size(max = 10, message = "제목은 10자 이하로 입력해주세요.")
    private String title;

    @Column(columnDefinition = "longtext")
    private String content;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;


    public Todo(){

    }

    public Todo(String title, String content, Member member) {
        this.title = title;
        this.content = content;
        this.member = member;
    }

    public void updateTodo(String title, String content) {
        this.title = title;
        this.content = content;
    }

}
