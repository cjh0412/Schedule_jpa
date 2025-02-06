package com.example.schedule_jpa.entity;

import jakarta.persistence.*;
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
    private String title;

    @Column(columnDefinition = "longtext")
    private String content;

//    @Column(nullable = false)
//    private String author;

    //todo member 매핑 필요
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;


    public Todo(){

    }

    public Todo(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void updateTodo(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void setMember(Member member) {
        this.member = member;
    }
}
