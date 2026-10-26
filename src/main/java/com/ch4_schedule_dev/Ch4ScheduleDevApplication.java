package com.ch4_schedule_dev;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class Ch4ScheduleDevApplication {
    public static void main(String[] args) {
        SpringApplication.run(Ch4ScheduleDevApplication.class, args);
    }
}
