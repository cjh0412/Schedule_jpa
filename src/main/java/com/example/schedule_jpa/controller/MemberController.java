package com.example.schedule_jpa.controller;

import com.example.schedule_jpa.dto.MemberRequestDto;
import com.example.schedule_jpa.dto.MemberResponseDto;
import com.example.schedule_jpa.service.MemberService;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<MemberResponseDto> save(@RequestBody MemberRequestDto requestDto){
        MemberResponseDto responseDto = memberService.save(requestDto.getUsername(),  requestDto.getEmail());
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<MemberResponseDto>> findAll(){
        List<MemberResponseDto> responseDtoList = memberService.findAll();
        return new ResponseEntity<>(responseDtoList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberResponseDto> findById(@PathVariable Long id){
        MemberResponseDto responseDto = memberService.findByIdOrElseThrow(id);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateMember(@PathVariable Long id, @RequestBody MemberRequestDto requestDto){
        memberService.updateMember(id, requestDto.getUsername(), requestDto.getEmail());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable Long id){
        memberService.deleteMember(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
