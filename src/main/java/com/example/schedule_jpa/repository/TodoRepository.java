package com.example.schedule_jpa.repository;

import com.example.schedule_jpa.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long> {
}
