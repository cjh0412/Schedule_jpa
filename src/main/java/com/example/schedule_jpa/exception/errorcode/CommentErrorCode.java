package com.example.schedule_jpa.exception.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CommentErrorCode {
    TODO_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 할일입니다."),
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "조회된 댓글이 없습니다."),
    NO_EDITABLE_COMMENT(HttpStatus.NOT_FOUND, "수정할 수 있는 댓글이 없습니다."),
    COMMENT_EDITABLE_NOT_ALLOWED(HttpStatus.UNAUTHORIZED,"댓글 작성자만 수정이 가능합니다."),
    NO_DELETED_COMMENT(HttpStatus.NOT_FOUND, "삭제할 수 있는 댓글이 없습니다."),
    COMMENT_DELETED_NOT_ALLOWED(HttpStatus.UNAUTHORIZED,"댓글 작성자만 삭제가 가능합니다.")
    ;

    private final HttpStatus httpStatus;
    private final String message;
    }

