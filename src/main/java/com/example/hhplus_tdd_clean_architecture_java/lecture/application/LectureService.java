package com.example.hhplus_tdd_clean_architecture_java.lecture.application;

import com.example.hhplus_tdd_clean_architecture_java.lecture.domain.Application;
import com.example.hhplus_tdd_clean_architecture_java.lecture.domain.Lecture;
import com.example.hhplus_tdd_clean_architecture_java.lecture.interfaces.ApplicationRepository;
import com.example.hhplus_tdd_clean_architecture_java.lecture.interfaces.LectureRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class LectureService {
    private final LectureRepository lectureRepository;
    private final ApplicationRepository applicationRepository;

    public LectureService(LectureRepository lectureRepository, ApplicationRepository applicationRepository) {
        this.lectureRepository = lectureRepository;
        this.applicationRepository = applicationRepository;
    }

    public Application applyForLecture(Long userId, Long lectureId) {
        Lecture lecture = lectureRepository.findById(lectureId)
                .orElseThrow(() -> new IllegalArgumentException("해당 강의는 찾을 수 없다."));

        if (!lecture.hasAvailableCapacity()) {
            throw new IllegalStateException("해당 강의를 더이상 선택 할 수 없다.");
        }

        if (applicationRepository.existsByUserIdAndLectureId(userId, lectureId)) {
            throw new IllegalStateException("해당 유저는 이미 신청했다.");
        }

        lecture.reduceCapacity();
        lectureRepository.save(lecture);

        Application application = new Application(userId, lectureId);
        application.accept();
        applicationRepository.save(application);

        return application;
    }

    public List<Lecture> getAllLectures() {
        return lectureRepository.findAll();
    }
}
