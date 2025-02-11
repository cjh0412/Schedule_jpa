package com.example.schedule_jpa.controller;

import com.example.schedule_jpa.command.CreateMemberCommand;
import com.example.schedule_jpa.command.UpdateMemberCommand;
import com.example.schedule_jpa.dto.MemberRequestDto;
import com.example.schedule_jpa.dto.MemberResponseDto;
import com.example.schedule_jpa.entity.Member;
import com.example.schedule_jpa.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/signUp")
    public ResponseEntity<MemberResponseDto> save(@Valid @RequestBody MemberRequestDto requestDto){
        CreateMemberCommand memberCommand = new CreateMemberCommand(requestDto.getUsername(), requestDto.getEmail(), requestDto.getPassword());
        MemberResponseDto responseDto = memberService.save(memberCommand);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<MemberResponseDto> loginMember(HttpServletRequest request,  @RequestBody MemberRequestDto requestDto){
        MemberResponseDto responseDto = memberService.memberLogin(requestDto.getEmail(), requestDto.getPassword());

        HttpSession session = request.getSession(true);
        session.setAttribute("token", responseDto.getId());
        return new ResponseEntity<>(responseDto,HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<MemberResponseDto>> findAll(@PageableDefault(sort = "modifiedAt", direction = Sort.Direction.DESC)Pageable pageable){
        Page<MemberResponseDto> responseDtoPage = memberService.findAll(pageable);
        return new ResponseEntity<>(responseDtoPage, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberResponseDto> findById(@PathVariable Long id){
        Member member = memberService.findById(id);
        return new ResponseEntity<>(new MemberResponseDto(member.getId(), member.getUsername(), member.getEmail()), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateMember(@PathVariable Long id, @RequestBody MemberRequestDto requestDto){
        UpdateMemberCommand memberCommand = new UpdateMemberCommand(id, requestDto.getUsername(), requestDto.getEmail(), requestDto.getPassword());
        memberService.updateMember(memberCommand);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable Long id, @RequestParam String password){
        memberService.deleteMember(id, password);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
