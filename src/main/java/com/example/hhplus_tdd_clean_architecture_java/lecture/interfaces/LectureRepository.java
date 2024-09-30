package com.example.hhplus_tdd_clean_architecture_java.lecture.interfaces;

import com.example.hhplus_tdd_clean_architecture_java.lecture.domain.Lecture;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LectureRepository {
    Optional<Lecture> findById(Long id);
    void save(Lecture lecture);
    List<Lecture> findAll();
}
