package com.ch4_schedule_dev.controller;

import com.ch4_schedule_dev.dto.ScheduleResponseDto;
import com.ch4_schedule_dev.dto.ScheduleItemRequestDto; // 👈 가지고 계신 DTO 임포트
import com.ch4_schedule_dev.service.ScheduleService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    // 1. 일정 생성 (POST)
    @PostMapping
    public ScheduleResponseDto createSchedule(@RequestBody ScheduleItemRequestDto requestDto) {
        return scheduleService.createSchedule(requestDto);
    }

    // 2. 일정 전체 조회 (GET)
    @GetMapping
    public List<ScheduleResponseDto> getAllSchedules() {
        return scheduleService.getAllSchedules();
    }

    // 3. 일정 단건 조회 (GET)
    @GetMapping("/{id}")
    public ScheduleResponseDto getSchedule(@PathVariable Long id) {
        return scheduleService.getSchedule(id);
    }

    // 4. 일정 수정 (PUT)
    @PutMapping("/{id}")
    public ScheduleResponseDto updateSchedule(
            @PathVariable Long id,
            @RequestBody ScheduleItemRequestDto requestDto) {
        return scheduleService.updateSchedule(id, requestDto);
    }

    // 5. 일정 삭제 (DELETE)
    @DeleteMapping("/{id}")
    public String deleteSchedule(@PathVariable Long id) {
        scheduleService.deleteSchedule(id);
        return "ID " + id + "번 일정이 성공적으로 삭제되었습니다.";
    }
}