package com.example.hhplus_tdd_clean_architecture_java.application.lecture;

import com.example.hhplus_tdd_clean_architecture_java.domain.lecture.Application;
import com.example.hhplus_tdd_clean_architecture_java.domain.lecture.Lecture;
import com.example.hhplus_tdd_clean_architecture_java.infrastructure.persistence.ApplicationEntity;
import com.example.hhplus_tdd_clean_architecture_java.infrastructure.persistence.ApplicationRepository;
import com.example.hhplus_tdd_clean_architecture_java.infrastructure.persistence.LectureRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class LectureServiceImpl implements LectureService {
    private final LectureRepository lectureRepository;
    private final ApplicationRepository applicationRepository;

    public LectureServiceImpl(LectureRepository lectureRepository, ApplicationRepository applicationRepository) {
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

        ApplicationEntity entity = new ApplicationEntity(userId, lectureId);
        applicationRepository.save(entity);

        Application application = entity.toDomain();
        application.accept();
        return application;
    }

    public List<Lecture> getAllLectures() {
        return lectureRepository.findAll();
    }
}
