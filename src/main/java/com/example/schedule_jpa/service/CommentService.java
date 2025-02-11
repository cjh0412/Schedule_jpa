package com.example.schedule_jpa.service;

import com.example.schedule_jpa.command.CreateCommentCommand;
import com.example.schedule_jpa.command.UpdateCommentCommand;
import com.example.schedule_jpa.dto.CommentRequestDto;
import com.example.schedule_jpa.dto.CommentResponseDto;
import com.example.schedule_jpa.entity.Comment;
import com.example.schedule_jpa.entity.Member;
import com.example.schedule_jpa.entity.Todo;
import com.example.schedule_jpa.exception.CommentException;
import com.example.schedule_jpa.exception.errorcode.CommentErrorCode;
import com.example.schedule_jpa.repository.CommentRepository;
import com.example.schedule_jpa.repository.MemberRepository;
import com.example.schedule_jpa.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final TodoService todoService;
    private final MemberService memberService;

    public CommentResponseDto save(CreateCommentCommand commentCommand){
        Member findMember = memberService.findById(commentCommand.getMemberId());
        Todo todo = todoService.findById(commentCommand.getTodoId());

        Comment comment = new Comment(commentCommand.getContent(), findMember, todo);
        commentRepository.save(comment);

        return new CommentResponseDto(comment.getId(),
                comment.getTodo().getId(),
                comment.getContent(),
                comment.getMember().getId(),
                comment.getMember().getUsername(),
                comment.getCreatedAt());
    }

    public List<CommentResponseDto> findAll(){
        return commentRepository.findAll()
                .stream()
                .map(CommentResponseDto :: toDto)
                .toList();
    }

    public Comment findById(Long id){
        return commentRepository.findById(id)
                .orElseThrow(() -> new CommentException(CommentErrorCode.COMMENT_NOT_FOUND));
    }

    public void updateComment(UpdateCommentCommand commentCommand){
        Comment comment = commentRepository.findById(commentCommand.getId())
                .orElseThrow(() -> new CommentException(CommentErrorCode.NO_EDITABLE_COMMENT));

        if(!commentCommand.getMemberId().equals(comment.getMember().getId())){
            throw new  CommentException(CommentErrorCode.COMMENT_DELETED_NOT_ALLOWED);
        }

        comment.update(commentCommand.getContent());
    }

    public void deleteComment(Long id, Long memberId){
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new CommentException(CommentErrorCode.NO_DELETED_COMMENT));
        if(!memberId.equals(comment.getMember().getId())){
            throw new CommentException(CommentErrorCode.COMMENT_DELETED_NOT_ALLOWED);
        }

        commentRepository.deleteById(id);
    }

}
