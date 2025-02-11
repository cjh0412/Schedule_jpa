package com.example.schedule_jpa.service;

import com.example.schedule_jpa.command.CreateTodoCommand;
import com.example.schedule_jpa.command.UpdateTodoCommand;
import com.example.schedule_jpa.dto.TodoRequestDto;
import com.example.schedule_jpa.dto.TodoResponseDto;
import com.example.schedule_jpa.entity.Member;
import com.example.schedule_jpa.entity.Todo;
import com.example.schedule_jpa.repository.MemberRepository;
import com.example.schedule_jpa.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public TodoResponseDto save(CreateTodoCommand command){

        if(!memberRepository.existsById(command.getMemberId())){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 회원을 찾을 수 없습니다.");
        }

        Member findMember = memberRepository.getReferenceById(command.getMemberId());
        Todo todo = new Todo(command.getTitle(), command.getContent(), findMember);

        todoRepository.save(todo);
        return new TodoResponseDto(todo.getId(),   todo.getTitle(), todo.getContent() , todo.getMember().getId(), todo.getMember().getUsername());
    }

    public Page<TodoResponseDto> findAll(Pageable pageable) {
        return  todoRepository.findAll(pageable)
                .map(TodoResponseDto :: toDto);
    }

    public TodoResponseDto findById(Long id) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "조회된 정보가 없습니다."));
        return new TodoResponseDto(todo.getId(), todo.getTitle(), todo.getContent(), todo.getMember().getId(), todo.getMember().getUsername());
    }

    @Transactional
    public void updateTodo(UpdateTodoCommand command) {
        Todo todo = todoRepository.findById(command.getId())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "수정할 수 있는 데이터가 없습니다."));

        if(!command.getMemberId().equals(todo.getMember().getId())){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "일정은 작성자만 수정이 가능합니다.");
        }

        todo.updateTodo(command.getTitle(), command.getContent());
    }

    public void deleteTodo(Long memberId ,Long id) {

        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "삭제할 수 있는 데이터가 없습니다."));

        if(!memberId.equals(todo.getMember().getId())){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "일정은 작성자만 삭제가 가능합니다.");
        }

        todoRepository.deleteById(id);
    }
}
