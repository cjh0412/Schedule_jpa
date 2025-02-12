package com.example.schedule_jpa.service;

import com.example.schedule_jpa.command.CreateTodoCommand;
import com.example.schedule_jpa.command.UpdateTodoCommand;
import com.example.schedule_jpa.dto.TodoResponseDto;
import com.example.schedule_jpa.entity.Member;
import com.example.schedule_jpa.entity.Todo;
import com.example.schedule_jpa.exception.TodoException;
import com.example.schedule_jpa.exception.errorcode.TodoErrorCode;
import com.example.schedule_jpa.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;
    private final MemberService memberService;

    public TodoResponseDto save(CreateTodoCommand command){

        Member findMember = memberService.findById(command.getMemberId());
        Todo todo = new Todo(command.getTitle(), command.getContent(), findMember);

        todoRepository.save(todo);
        return TodoResponseDto.toDto(todo);
    }

    public Page<TodoResponseDto> findAll(Pageable pageable) {
        return  todoRepository.findAll(pageable)
                .map(TodoResponseDto :: toDto);
    }

    public Todo findById(Long id) {
        return todoRepository.findById(id)
                .orElseThrow(() -> new TodoException(TodoErrorCode.NOT_FOUND_TODO));
    }

    @Transactional
    public void updateTodo(UpdateTodoCommand command) {
        Todo todo = todoRepository.findById(command.getId())
                        .orElseThrow(() -> new TodoException(TodoErrorCode.NO_EDITABLE_TODO));

        if(!command.getMemberId().equals(todo.getMember().getId())){
            throw new TodoException(TodoErrorCode.TODO_EDITABLE_NOT_ALLOWED);
        }

        todo.updateTodo(command.getTitle(), command.getContent());
    }

    public void deleteTodo(Long memberId ,Long id) {

        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new TodoException(TodoErrorCode.NO_DELETED_TODO));

        if(!memberId.equals(todo.getMember().getId())){
            throw new TodoException(TodoErrorCode.TODO_DELETED_NOT_ALLOWED);
        }

        todoRepository.deleteById(id);
    }
}
