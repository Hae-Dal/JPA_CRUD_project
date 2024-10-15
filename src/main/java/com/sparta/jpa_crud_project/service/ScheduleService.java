package com.sparta.jpa_crud_project.service;

import com.sparta.jpa_crud_project.dto.ScheduleRequestDto;
import com.sparta.jpa_crud_project.dto.ScheduleResponseDto;
import com.sparta.jpa_crud_project.entity.Schedule;
import com.sparta.jpa_crud_project.entity.User;
import com.sparta.jpa_crud_project.repository.ScheduleRepository;
import com.sparta.jpa_crud_project.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    public ScheduleService(ScheduleRepository scheduleRepository, UserRepository userRepository) {
        this.scheduleRepository = scheduleRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public ScheduleResponseDto createSchedule(ScheduleRequestDto scheduleRequestDto) {
        User author = userRepository.findById(scheduleRequestDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Schedule schedule = new Schedule();
        schedule.setTitle(scheduleRequestDto.getTitle());
        schedule.setContent(scheduleRequestDto.getContent());
        schedule.setAuthor(author);
        scheduleRepository.save(schedule);

        return new ScheduleResponseDto(schedule);
    }

    @Transactional(readOnly = true)
    public List<ScheduleResponseDto> getAllSchedules() {
        return scheduleRepository.findAll().stream()
                .map(ScheduleResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<ScheduleResponseDto> getSchedulesPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("updatedDate").descending());
        Page<Schedule> schedulePage = scheduleRepository.findAll(pageable);
        return schedulePage.map(ScheduleResponseDto::new);
    }

    @Transactional
    public ScheduleResponseDto updateSchedule(Long id, ScheduleRequestDto scheduleRequestDto) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Schedule not found"));

        schedule.setTitle(scheduleRequestDto.getTitle());
        schedule.setContent(scheduleRequestDto.getContent());
        scheduleRepository.save(schedule);

        return new ScheduleResponseDto(schedule);
    }

    @Transactional
    public void deleteSchedule(Long id) {
        scheduleRepository.deleteById(id);
    }
}
