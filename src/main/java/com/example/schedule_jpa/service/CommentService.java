package com.example.schedule_jpa.service;

import com.example.schedule_jpa.command.CreateCommentCommand;
import com.example.schedule_jpa.command.UpdateCommentCommand;
import com.example.schedule_jpa.dto.CommentRequestDto;
import com.example.schedule_jpa.dto.CommentResponseDto;
import com.example.schedule_jpa.entity.Comment;
import com.example.schedule_jpa.entity.Member;
import com.example.schedule_jpa.entity.Todo;
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
    private final TodoRepository todoRepository;
    private final MemberRepository memberRepository;

    public CommentResponseDto save(CreateCommentCommand commentCommand){
        Member findMember = memberRepository.getReferenceById(commentCommand.getMemberId());
        Todo todo = todoRepository.findById(commentCommand.getTodoId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "할일 정보를 다시 조회해 주세요"));

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
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "조회된 댓글이 없습니다."));
    }

    public void updateComment(UpdateCommentCommand commentCommand){
        Comment comment = commentRepository.findById(commentCommand.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "수정할 수 있는 댓글이 없습니다."));

        if(!commentCommand.getMemberId().equals(comment.getMember().getId())){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "댓글 작성자만 수정이 가능합니다.");
        }

        comment.update(commentCommand.getContent());
    }

    public void deleteComment(Long id, Long memberId){
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "삭제할 수 있는 댓글이 없습니다."));
        if(!memberId.equals(comment.getMember().getId())){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "댓글 작성자만 수정이 가능합니다.");
        }

        commentRepository.deleteById(id);
    }

}
