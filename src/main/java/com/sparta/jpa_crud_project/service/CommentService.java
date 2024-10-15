package com.sparta.jpa_crud_project.service;

import com.sparta.jpa_crud_project.dto.CommentRequestDto;
import com.sparta.jpa_crud_project.dto.CommentResponseDto;
import com.sparta.jpa_crud_project.entity.Comment;
import com.sparta.jpa_crud_project.entity.Schedule;
import com.sparta.jpa_crud_project.repository.CommentRepository;
import com.sparta.jpa_crud_project.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final ScheduleRepository scheduleRepository;

    public CommentService(CommentRepository commentRepository, ScheduleRepository scheduleRepository) {
        this.commentRepository = commentRepository;
        this.scheduleRepository = scheduleRepository;
    }

    public CommentResponseDto createComment(Long scheduleId, CommentRequestDto commentRequestDto) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new IllegalArgumentException("Schedule not found"));
        Comment comment = new Comment();
        comment.setContent(commentRequestDto.getContent());
        comment.setUserName(commentRequestDto.getUserName());
        comment.setSchedule(schedule);
        commentRepository.save(comment);
        return new CommentResponseDto(comment);
    }

    public List<CommentResponseDto> getCommentsByScheduleId(Long scheduleId) {
        return commentRepository.findAll().stream()
                .filter(comment -> comment.getSchedule().getId().equals(scheduleId))
                .map(CommentResponseDto::new)
                .collect(Collectors.toList());
    }

    public CommentResponseDto updateComment(Long id, CommentRequestDto commentRequestDto) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found"));
        comment.setContent(commentRequestDto.getContent());
        commentRepository.save(comment);
        return new CommentResponseDto(comment);
    }

    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }
}
