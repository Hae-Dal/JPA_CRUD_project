package com.sparta.jpa_crud_project.dto;

import com.sparta.jpa_crud_project.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {
    private Long id;
    private String content;
    private String userName;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private Long scheduleId;

    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.userName = comment.getUserName();
        this.createdDate = comment.getCreatedDate();
        this.updatedDate = comment.getUpdatedDate();
        this.scheduleId = comment.getSchedule().getId();
    }
}
