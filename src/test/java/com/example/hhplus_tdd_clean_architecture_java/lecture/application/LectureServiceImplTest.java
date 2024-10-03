package com.example.hhplus_tdd_clean_architecture_java.lecture.application;

import com.example.hhplus_tdd_clean_architecture_java.application.lecture.LectureServiceImpl;
import com.example.hhplus_tdd_clean_architecture_java.domain.lecture.Application;
import com.example.hhplus_tdd_clean_architecture_java.domain.lecture.Lecture;
import com.example.hhplus_tdd_clean_architecture_java.infrastructure.persistence.ApplicationRepository;
import com.example.hhplus_tdd_clean_architecture_java.infrastructure.persistence.LectureRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@SpringBootTest
class LectureServiceImplTest {
    @Autowired
    private LectureServiceImpl lectureServiceImpl;

    @MockBean
    private LectureRepository lectureRepository;

    @MockBean
    private ApplicationRepository applicationRepository;

    @Test
    public void apply_for_lecture_success() {
        // Given
        Lecture lecture = new Lecture("항해99", 10); // 처음 capacity 10개
        given(lectureRepository.findById(anyLong())).willReturn(Optional.of(lecture));
        given(applicationRepository.existsByUserIdAndLectureId(anyLong(), anyLong())).willReturn(false);

        // When
        Application application = lectureServiceImpl.applyForLecture(1L, 1L);

        // Then
        assertNotNull(application);
        assertEquals("ACCEPTED", application.getStatus());
        assertEquals(9, lecture.getCapacity()); // 신청 후 capacity 9개
    }

    @Test
    public void apply_for_lecture_Full() {
        // Given
        Lecture lecture = new Lecture("항해99", 0); // capacity 0
        given(lectureRepository.findById(anyLong())).willReturn(Optional.of(lecture));

        // When & Then
        assertThrows(IllegalStateException.class, () -> {
            lectureServiceImpl.applyForLecture(1L, 1L);
        });
    }

    @Test
    public void apply_for_lecture_user_already_applied() {
        // Given
        Lecture lecture = new Lecture("항해99", 10);
        given(lectureRepository.findById(anyLong())).willReturn(Optional.of(lecture));
        given(applicationRepository.existsByUserIdAndLectureId(anyLong(), anyLong())).willReturn(true); // 중복 신청

        // When & Then
        assertThrows(IllegalStateException.class, () -> {
            lectureServiceImpl.applyForLecture(1L, 1L);
        });
    }

    @Test
    public void get_all_lectures_returns_lectures() {
        // Given
        List<Lecture> lectures = Arrays.asList(
                new Lecture("자바", 10),
                new Lecture("코틀린", 20)
        );
        given(lectureRepository.findAll()).willReturn(lectures);

        // When
        List<Lecture> result = lectureServiceImpl.getAllLectures();

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("자바", result.get(0).getTitle());
        assertEquals("코틀린", result.get(1).getTitle());
    }
}
