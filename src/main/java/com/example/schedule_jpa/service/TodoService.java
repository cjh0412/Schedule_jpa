package com.example.schedule_jpa.service;

import com.example.schedule_jpa.dto.TodoResponseDto;
import com.example.schedule_jpa.entity.Member;
import com.example.schedule_jpa.entity.Todo;
import com.example.schedule_jpa.repository.MemberRepository;
import com.example.schedule_jpa.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;
    private final MemberRepository memberRepository;

    public TodoResponseDto save(Long memberId, String title, String content){
        // 로그인 여부 체크
        Member findMember = memberRepository.findByIdOrElseThrow(memberId);
        if(!memberId.equals(findMember.getId())){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "로그인 후 사용해주시기 바랍니다.");
        }

        Todo todo = new Todo(title, content);
        todo.setMember(findMember);

        todoRepository.save(todo);
        return new TodoResponseDto(todo.getId(),   todo.getTitle(), todo.getContent() , todo.getMember().getId(), todo.getMember().getUsername());
    }

    public List<TodoResponseDto> findAll(Long memberId) {
        memberRepository.findByIdOrElseThrow(memberId);

        return  todoRepository.findAll()
                .stream()
                .map(TodoResponseDto :: toDto)
                .toList();
    }

    public TodoResponseDto findById(Long memberId, Long id) {
        memberRepository.findByIdOrElseThrow(memberId);

        Todo todo = todoRepository.findByIdOrElseThrow(id);
        return new TodoResponseDto(todo.getId(), todo.getTitle(), todo.getContent(), todo.getMember().getId(), todo.getMember().getUsername());
    }

    @Transactional
    public void updateTodo(Long memberId ,Long id, String title, String content) {
        memberRepository.findByIdOrElseThrow(memberId);
        Todo todo = todoRepository.findByIdOrElseThrow(id);

        if(!id.equals(todo.getId())){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "수정할 수 있는 데이터가 없습니다.");
        }

        todo.updateTodo(title, content);

    }

    public void deleteTodo(Long memberId ,Long id) {

        Member findMember = memberRepository.findByIdOrElseThrow(memberId);
        Todo todo = todoRepository.findByIdOrElseThrow(id);

        if(!id.equals(todo.getId())){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "삭제할 수 있는 데이터가 없습니다.");
        }

        todoRepository.deleteById(id);
    }
}
