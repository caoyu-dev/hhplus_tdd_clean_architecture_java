package com.example.hhplus_tdd_clean_architecture_java.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationRepository extends JpaRepository<ApplicationEntity, Long> {
    boolean existsByUserIdAndLectureId(Long userId, Long lectureId);
}
