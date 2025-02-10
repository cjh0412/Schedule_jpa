package com.example.schedule_jpa.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "comment")
public class Comment extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "todo_id")
    private Todo todo;

    public Comment(){

    }

    public Comment(String content, Member member, Todo todo) {
        this.content = content;
        this.member = member;
        this.todo = todo;
    }

    public void update(String content){
        this.content = content;
    }
}
