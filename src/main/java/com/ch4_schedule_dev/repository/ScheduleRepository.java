package com.ch4_schedule_dev.repository;

import com.ch4_schedule_dev.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}