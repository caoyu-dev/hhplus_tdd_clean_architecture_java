package com.example.hhplus_tdd_clean_architecture_java.presentation.lecture;

import com.example.hhplus_tdd_clean_architecture_java.application.lecture.LectureService;
import com.example.hhplus_tdd_clean_architecture_java.domain.lecture.Lecture;
import com.example.hhplus_tdd_clean_architecture_java.infrastructure.persistence.ApplicationRepository;
import com.example.hhplus_tdd_clean_architecture_java.infrastructure.persistence.LectureRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class LectureIntegrationTest {
    @Autowired
    private LectureService lectureService;
    @Autowired
    private LectureRepository lectureRepository;
    @Autowired
    private ApplicationRepository applicationRepository;

    @Test
    void test_concurrent_applications() throws InterruptedException {
        Lecture lecture = new Lecture();
        lecture.setTitle("항해99");
        lecture.setLecturer("허재님");
        lecture.setLectureDate(LocalDate.of(2024, 01, 01));
        lectureRepository.save(lecture);

        ExecutorService executorService = Executors.newFixedThreadPool(40);
        CountDownLatch latch = new CountDownLatch(40);

        for (int i = 0; i < 40; i++) {
            final Long userId = Long.valueOf(i);
            executorService.submit(() -> {
                try {
                    latch.await();
                    lectureService.applyForLecture(lecture.getId(), userId);
                } catch (Exception e) {
                }
            });
        }

        latch.countDown();

        executorService.shutdown();
        executorService.awaitTermination(10, TimeUnit.SECONDS);

        long count = applicationRepository.countByLecture(lecture);
        assertEquals(30, count);  // 30명만 성공
    }
}
