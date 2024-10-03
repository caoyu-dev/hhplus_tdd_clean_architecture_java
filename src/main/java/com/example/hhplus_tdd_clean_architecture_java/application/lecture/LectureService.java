package com.example.hhplus_tdd_clean_architecture_java.application.lecture;

import com.example.hhplus_tdd_clean_architecture_java.domain.lecture.Application;
import com.example.hhplus_tdd_clean_architecture_java.domain.lecture.Lecture;

import java.util.List;

public interface LectureService {
    boolean applyForLecture(Long userId, Long lectureId);
    List<Lecture> getAllLectures();
    List<Application> getUserApplications(Long userId);
}
