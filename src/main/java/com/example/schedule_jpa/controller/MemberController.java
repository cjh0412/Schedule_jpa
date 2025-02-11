package com.example.schedule_jpa.controller;

import com.example.schedule_jpa.dto.MemberRequestDto;
import com.example.schedule_jpa.dto.MemberResponseDto;
import com.example.schedule_jpa.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
        MemberResponseDto responseDto = memberService.save(requestDto.getUsername(),  requestDto.getEmail(), requestDto.getPassword());
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
    public ResponseEntity<Page<MemberResponseDto>> findAll(Pageable pageable){
        Page<MemberResponseDto> responseDtoPage = memberService.findAll(pageable);
        return new ResponseEntity<>(responseDtoPage, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberResponseDto> findById(@PathVariable Long id){
        MemberResponseDto responseDto = memberService.findById(id);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateMember(@PathVariable Long id, @RequestBody MemberRequestDto requestDto){
        memberService.updateMember(id,
                requestDto.getUsername(),
                requestDto.getEmail(),
                requestDto.getPassword());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable Long id, @RequestParam String password){
        memberService.deleteMember(id, password);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
