package com.example.schedule_jpa.repository;

import com.example.schedule_jpa.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findById(Long id);
    Optional<Member> findByEmailAndPassword(String email, String password);
}
