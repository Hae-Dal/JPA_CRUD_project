package com.sparta.jpa_crud_project.dto;

import com.sparta.jpa_crud_project.entity.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserResponseDto {
    private Long id;
    private String userName;
    private String email;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    public UserResponseDto(User user) {
        this.id = user.getId();
        this.userName = user.getUserName();
        this.email = user.getEmail();
        this.createdDate = user.getCreatedDate();
        this.updatedDate = user.getUpdatedDate();
    }
}
