package com.example.hhplus_tdd_clean_architecture_java.application.lecture;

import com.example.hhplus_tdd_clean_architecture_java.domain.lecture.Application;
import com.example.hhplus_tdd_clean_architecture_java.domain.lecture.Lecture;
import com.example.hhplus_tdd_clean_architecture_java.infrastructure.persistence.ApplicationRepository;
import com.example.hhplus_tdd_clean_architecture_java.infrastructure.persistence.LectureRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class LectureServiceImpl implements LectureService {
    private final LectureRepository lectureRepository;
    private final ApplicationRepository applicationRepository;
    private final ReentrantLock lock = new ReentrantLock();

    public LectureServiceImpl(LectureRepository lectureRepository, ApplicationRepository applicationRepository) {
        this.lectureRepository = lectureRepository;
        this.applicationRepository = applicationRepository;
    }

    @Transactional
    public boolean applyForLecture(Long userId, Long lectureId) {
        lock.lock();
        try {
            Lecture lecture = lectureRepository.findById(lectureId)
                    .orElseThrow(() -> new IllegalArgumentException("해당 강의는 찾을 수 없다."));

            long count = applicationRepository.countByLecture(lecture);
            if (count >= 30) {
                throw new IllegalStateException("해당 강의를 더이상 선택 할 수 없다.");
            }

            if (applicationRepository.existsByUserIdAndLectureId(userId, lectureId)) {
                throw new IllegalStateException("해당 유저는 이미 신청했다.");
            }

            Application application = new Application();
            application.setUserId(userId);
            application.setLecture(lecture);
            application.setApplicationTime(LocalDateTime.now());
            applicationRepository.save(application);

            return true;
        } finally {
            lock.unlock();
        }
    }

    public List<Lecture> getAllLectures() {
        return lectureRepository.findAll();
    }

    public List<Application> getUserApplications(Long userId) {
        return applicationRepository.findByUserId(userId);
    }
}
