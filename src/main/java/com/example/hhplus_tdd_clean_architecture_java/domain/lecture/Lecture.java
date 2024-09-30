package com.example.hhplus_tdd_clean_architecture_java.domain.lecture;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Lecture {
    private Long id;
    private String title;
    private int capacity;
    private LocalDateTime createdAt;

    public Lecture(String title, int capacity) {
        this.title = title;
        this.capacity = capacity;
        this.createdAt = LocalDateTime.now();
    }

    public boolean hasAvailableCapacity() {
        return this.capacity > 0;
    }

    public void reduceCapacity() {
        if (capacity > 0) {
            this.capacity--;
        } else {
            throw new IllegalStateException("해당 강의는 자리가 없다.");
        }
    }
}
