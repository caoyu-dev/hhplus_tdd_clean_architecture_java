package com.example.hhplus_tdd_clean_architecture_java.infrastructure.persistence;

import com.example.hhplus_tdd_clean_architecture_java.domain.lecture.Application;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "application")
@Getter
@NoArgsConstructor
public class ApplicationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long userId;
    @Column(nullable = false)
    private Long lectureId;
    @Column(nullable = false)
    private LocalDateTime createdAt;
    @Column(nullable = false)
    private String status;

    public ApplicationEntity(Long userId, Long lectureId) {
        this.userId = userId;
        this.lectureId = lectureId;
        this.createdAt = LocalDateTime.now();
        this.status = "PENDING";
    }

    public Application toDomain() {
        Application application = new Application(userId, lectureId);
        if ("ACCEPTED".equals(this.status)) {
            application.accept();
        } else if ("REJECTED".equals(this.status)) {
            application.reject();
        }
        return application;
    }
}
