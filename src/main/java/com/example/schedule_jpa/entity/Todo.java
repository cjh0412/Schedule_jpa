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

    @Column(nullable = false)
    private String author;

    //    @ManyToOne
//    @JoinColumn(name = "member_id")
//    private Member member;


    public Todo(){

    }

    public Todo(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public void updateTodo(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }
}
