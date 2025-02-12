package com.example.schedule_jpa.service;

import com.example.schedule_jpa.command.CreateMemberCommand;
import com.example.schedule_jpa.command.UpdateMemberCommand;
import com.example.schedule_jpa.config.PasswordEncoder;
import com.example.schedule_jpa.dto.MemberResponseDto;
import com.example.schedule_jpa.entity.Member;
import com.example.schedule_jpa.exception.MemberException;
import com.example.schedule_jpa.exception.errorcode.MemberErrorCode;
import com.example.schedule_jpa.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder encoder;

    public MemberResponseDto save(CreateMemberCommand command) {
        if (memberRepository.findByEmail(command.getEmail()).isPresent()) {
            throw new MemberException(MemberErrorCode.ALREADY_REGISTER_MEMBER);
        }

        Member member = new Member(command.getUsername(), command.getEmail(), encoder.encode(command.getPassword()));
        memberRepository.save(member);
        return MemberResponseDto.toDto(member);
    }

    public Page<MemberResponseDto> findAll(Pageable pageable) {
        return memberRepository.findAll(pageable)
                .map(MemberResponseDto::toDto);
    }

    // 재사용 가능성 높으므로 Member 반환
    public Member findById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new MemberException(MemberErrorCode.NOT_FOUND_MEMBER_ID));
    }

    public MemberResponseDto memberLogin(String email, String password) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberException(MemberErrorCode.NOT_FOUND_MEMBER));

        if (!encoder.matches(password, member.getPassword())) {
            throw new MemberException(MemberErrorCode.NOT_MATCHES_PASSWORD);
        }
        ;

        return new MemberResponseDto(member.getId(), member.getUsername(), member.getEmail());
    }

    @Transactional
    public void updateMember(UpdateMemberCommand command) {
        Member member = memberRepository.findById(command.getId())
                .orElseThrow(() -> new MemberException(MemberErrorCode.NO_EDITABLE_MEMBER));

        if (!encoder.matches(command.getPassword(), member.getPassword())) {
            throw new MemberException(MemberErrorCode.NOT_MATCHES_PASSWORD);
        }
        ;

        member.updateMember(command.getUsername(), command.getEmail());
    }

    public void deleteMember(Long id, String password) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new MemberException(MemberErrorCode.NO_DELETED_MEMBER));

        if (!encoder.matches(password, member.getPassword())) {
            throw new MemberException(MemberErrorCode.NOT_MATCHES_PASSWORD);
        }
        ;

        memberRepository.deleteById(member.getId());
    }
}
