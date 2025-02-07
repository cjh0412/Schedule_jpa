package com.example.schedule_jpa.service;

import com.example.schedule_jpa.dto.MemberResponseDto;
import com.example.schedule_jpa.entity.Member;
import com.example.schedule_jpa.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberResponseDto save(String username, String email, String password) {
        Member member = new Member(username, email, password);
         memberRepository.save(member);
        return new MemberResponseDto(member.getId(), member.getUsername(), member.getEmail());
    }

    public List<MemberResponseDto> findAll() {
        return memberRepository.findAll()
                .stream()
                .map(MemberResponseDto::toDto)
                .toList();
    }

    public MemberResponseDto findById(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "조회된 정보가 없습니다."));
        return new MemberResponseDto(member.getId(), member.getUsername(), member.getEmail());
    }

    public MemberResponseDto findByEmailAndPassword(String email, String password) {
        Member member = memberRepository.findByEmailAndPassword(email, password)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "이메일과 비밀번호 정보가 일치하지 않습니다."));
        return new MemberResponseDto(member.getId(), member.getUsername(), member.getEmail());
    }

    @Transactional
    public void updateMember(Long id, String username, String email, String password) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "수정할 수 있는 데이터가 없습니다."));

        if(!password.equals(member.getPassword())){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "잘못된 비밀번호 입니다.");
        }

        member.updateMember(username, email);
    }

    public void deleteMember(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "삭제할 수 있는 데이터가 없습니다."));
        memberRepository.deleteById(member.getId());
    }
}
