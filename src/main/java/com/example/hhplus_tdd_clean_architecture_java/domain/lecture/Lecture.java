package com.example.hhplus_tdd_clean_architecture_java.domain.lecture;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Lecture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String lecturer;
    private LocalDate lectureDate;

    @OneToMany(mappedBy = "lecture", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Application> applications = new HashSet<>();

    private int maxCapacity = 30;

    public Lecture() {}

    public Lecture(String title, String lecturer, LocalDate lectureDate) {
        this.title = title;
        this.lecturer = lecturer;
        this.lectureDate = lectureDate;
    }
}
