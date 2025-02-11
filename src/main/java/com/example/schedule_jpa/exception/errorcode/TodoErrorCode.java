package com.example.schedule_jpa.exception.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum TodoErrorCode {
    NOT_FOUND_TODO(HttpStatus.NOT_FOUND, "조회된 정보가 없습니다."),
    NO_EDITABLE_TODO(HttpStatus.NOT_FOUND, "수정할 수 있는 일정이 없습니다."),
    TODO_EDITABLE_NOT_ALLOWED(HttpStatus.UNAUTHORIZED,"일정은 작성자만 수정이 가능합니다."),
    NO_DELETED_TODO(HttpStatus.NOT_FOUND, "삭제할 수 있는 일정이 없습니다."),
    TODO_DELETED_NOT_ALLOWED(HttpStatus.UNAUTHORIZED,"일정은 작성자만 삭제가 가능합니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
