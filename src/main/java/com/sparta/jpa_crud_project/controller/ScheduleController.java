package com.sparta.jpa_crud_project.controller;

import com.sparta.jpa_crud_project.dto.ScheduleRequestDto;
import com.sparta.jpa_crud_project.dto.ScheduleResponseDto;
import com.sparta.jpa_crud_project.service.ScheduleService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping
    public ResponseEntity<ScheduleResponseDto> createSchedule(@RequestBody @Valid ScheduleRequestDto scheduleRequestDto) {
        ScheduleResponseDto createdSchedule = scheduleService.createSchedule(scheduleRequestDto);
        return ResponseEntity.ok().body(createdSchedule);
    }

    @GetMapping
    public ResponseEntity<List<ScheduleResponseDto>> getAllSchedules() {
        List<ScheduleResponseDto> schedules = scheduleService.getAllSchedules();
        return ResponseEntity.ok().body(schedules);
    }

    @GetMapping("/paged")
    public ResponseEntity<Page<ScheduleResponseDto>> getPagedSchedules(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<ScheduleResponseDto> schedules = scheduleService.getSchedulesPage(page, size);
        return ResponseEntity.ok().body(schedules);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> updateSchedule(@PathVariable Long id, @RequestBody @Valid ScheduleRequestDto scheduleRequestDto) {
        ScheduleResponseDto updatedSchedule = scheduleService.updateSchedule(id, scheduleRequestDto);
        return ResponseEntity.ok().body(updatedSchedule);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id) {
        scheduleService.deleteSchedule(id);
        return ResponseEntity.ok().build();
    }
}
