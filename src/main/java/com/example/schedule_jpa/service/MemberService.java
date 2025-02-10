package com.example.schedule_jpa.service;

import com.example.schedule_jpa.config.PasswordEncoder;
import com.example.schedule_jpa.dto.MemberRequestDto;
import com.example.schedule_jpa.dto.MemberResponseDto;
import com.example.schedule_jpa.entity.Member;
import com.example.schedule_jpa.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder encoder;

    public MemberResponseDto save(String username, String email, String password) {
        Member member = new Member(username, email, encoder.encode(password));
         memberRepository.save(member);
        return new MemberResponseDto(member.getId(), member.getUsername(), member.getEmail());
    }

    public List<MemberResponseDto> findAll(Pageable pageable) {
        return memberRepository.findAll(pageable)
                .stream()
                .map(MemberResponseDto::toDto)
                .toList();
    }

    public MemberResponseDto findById(Long id, Pageable pageable) {
        Member member = memberRepository.findById(id, pageable)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "조회된 정보가 없습니다."));
        return new MemberResponseDto(member.getId(), member.getUsername(), member.getEmail());
    }

    public MemberResponseDto memberLogin(String email, String password) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "존재하지 않는 이메일 정보입니다."));

        if(!encoder.matches(password, member.getPassword())){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");
        };

        return new MemberResponseDto(member.getId(), member.getUsername(), member.getEmail());
    }

    @Transactional
    public void updateMember(Long id, String username, String email, String password) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "수정할 수 있는 데이터가 없습니다."));

        if(!encoder.matches(password, member.getPassword())){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");
        };

        member.updateMember(username, email);
    }

    public void deleteMember(Long id, String password) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "삭제할 수 있는 데이터가 없습니다."));

        if(!encoder.matches(password, member.getPassword())){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");
        };

        memberRepository.deleteById(member.getId());
    }


}
