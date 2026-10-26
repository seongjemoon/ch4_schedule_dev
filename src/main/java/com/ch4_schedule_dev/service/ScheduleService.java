package com.ch4_schedule_dev.service;

import com.ch4_schedule_dev.dto.ScheduleResponseDto;
import com.ch4_schedule_dev.dto.ScheduleItemRequestDto;
import com.ch4_schedule_dev.entity.Schedule;
import com.ch4_schedule_dev.entity.User; // 💡 User 엔티티 임포트 추가
import com.ch4_schedule_dev.repository.ScheduleRepository;
import com.ch4_schedule_dev.repository.UserRepository; // 💡 UserRepository 임포트 추가
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    public ScheduleService(ScheduleRepository scheduleRepository, UserRepository userRepository) {
        this.scheduleRepository = scheduleRepository;
        this.userRepository = userRepository;
    }

    // 1. 일정 생성
    @Transactional
    public ScheduleResponseDto createSchedule(ScheduleItemRequestDto requestDto) {
        User user = userRepository.findById(requestDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다. id=" + requestDto.getUserId()));
        Schedule schedule = new Schedule(requestDto.getTitle(), requestDto.getContent(), user);

        Schedule savedSchedule = scheduleRepository.save(schedule);
        return new ScheduleResponseDto(savedSchedule);
    }

    // 2. 일정 전체 조회
    @Transactional(readOnly = true)
    public List<ScheduleResponseDto> getAllSchedules() {
        return scheduleRepository.findAll().stream()
                .map(ScheduleResponseDto::new)
                .collect(Collectors.toList());
    }

    // 3. 일정 단건 조회
    @Transactional(readOnly = true)
    public ScheduleResponseDto getSchedule(Long id) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 일정이 존재하지 않습니다. id=" + id));
        return new ScheduleResponseDto(schedule);
    }

    // 4. 일정 수정
    @Transactional
    public ScheduleResponseDto updateSchedule(Long id, ScheduleItemRequestDto requestDto) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 일정이 존재하지 않습니다. id=" + id));
        schedule.update(requestDto.getTitle(), requestDto.getContent());
        return new ScheduleResponseDto(schedule);
    }

    // 5. 일정 삭제
    @Transactional
    public void deleteSchedule(Long id) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 일정이 존재하지 않습니다. id=" + id));

        scheduleRepository.delete(schedule);

        log.info("일정이 성공적으로 삭제되었습니다. [Deleted Schedule ID: {}]", id);
    }
}
