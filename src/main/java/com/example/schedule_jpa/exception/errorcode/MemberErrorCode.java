package com.example.schedule_jpa.exception.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MemberErrorCode {
    ALREADY_REGISTER_MEMBER(HttpStatus.BAD_REQUEST, "이미 가입된 회원입니다."),
    NOT_FOUND_MEMBER_ID(HttpStatus.NOT_FOUND, "조회된 정보가 없습니다."),
    NOT_FOUND_MEMBER(HttpStatus.UNAUTHORIZED, "존재하지 않는 회원입니다."),
    NOT_MATCHES_PASSWORD(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다."),
    NO_EDITABLE_MEMBER(HttpStatus.NOT_FOUND, "수정할 수 있는 회원 정보가 존재하지 않습니다."),
    NO_DELETED_MEMBER(HttpStatus.NOT_FOUND, "삭제할 수 있는 회원 정보가 존재하지 않습니다.")
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
