package com.ch4_schedule_dev.dto;

public class ScheduleItemRequestDto {
    private Long userId;
    private String title;
    private String content;

    public ScheduleItemRequestDto() {}

    public Long getUserId() { return userId; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
}