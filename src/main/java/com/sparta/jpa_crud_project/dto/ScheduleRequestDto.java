package com.sparta.jpa_crud_project.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScheduleRequestDto {

    private Long userId;

    @NotBlank(message = "Title cannot be blank")
    @Size(max = 10, message = "Title must be 10 characters or less")
    private String title;

    @NotBlank(message = "Content cannot be blank")
    private String content;
}
