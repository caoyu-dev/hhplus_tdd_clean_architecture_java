package com.example.hhplus_tdd_clean_architecture_java.infrastructure.persistence;

import com.example.hhplus_tdd_clean_architecture_java.domain.lecture.Application;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationRepository {
    boolean existsByUserIdAndLectureId(Long userId, Long lectureId);
    void save(Application application);
}
