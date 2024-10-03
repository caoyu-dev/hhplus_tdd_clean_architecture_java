package com.example.hhplus_tdd_clean_architecture_java.infrastructure.persistence;

import com.example.hhplus_tdd_clean_architecture_java.domain.lecture.Application;
import com.example.hhplus_tdd_clean_architecture_java.domain.lecture.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
    boolean existsByUserIdAndLectureId(Long userId, Long lectureId);
    long countByLecture(Lecture lecture);
    List<Application> findByUserId(Long userId);
}
