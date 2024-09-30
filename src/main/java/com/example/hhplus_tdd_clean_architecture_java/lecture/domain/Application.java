package com.example.hhplus_tdd_clean_architecture_java.lecture.domain;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Application {
    private Long id;
    private Long userId;
    private Long lectureId;
    private LocalDateTime createdAt;
    private String status;

    public Application(Long userId, Long lectureId) {
        this.userId = userId;
        this.lectureId = lectureId;
        this.createdAt = LocalDateTime.now();
        this.status = "PENDING";
    }

    public void accept() {
        this.status = "ACCEPTED";
    }

    public void reject() {
        this.status = "REJECTED";
    }

    public boolean isAccepted() {
        return "ACCEPTED".equals(this.status);
    }
}
