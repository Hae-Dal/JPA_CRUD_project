package com.sparta.jpa_crud_project.controller;

import com.sparta.jpa_crud_project.dto.CommentRequestDto;
import com.sparta.jpa_crud_project.dto.CommentResponseDto;
import com.sparta.jpa_crud_project.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schedules/{scheduleId}/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<CommentResponseDto> createComment(@PathVariable Long scheduleId, @RequestBody CommentRequestDto commentRequestDto) {
        CommentResponseDto createdComment = commentService.createComment(scheduleId, commentRequestDto);
        return ResponseEntity.ok().body(createdComment);
    }

    @GetMapping
    public ResponseEntity<List<CommentResponseDto>> getCommentsByScheduleId(@PathVariable Long scheduleId) {
        List<CommentResponseDto> comments = commentService.getCommentsByScheduleId(scheduleId);
        return ResponseEntity.ok().body(comments);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentResponseDto> updateComment(@PathVariable Long id, @RequestBody CommentRequestDto commentRequestDto) {
        CommentResponseDto updatedComment = commentService.updateComment(id, commentRequestDto);
        return ResponseEntity.ok().body(updatedComment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.ok().build();
    }
}
