package com.example.hhplus_tdd_clean_architecture_java.lecture.application;

import com.example.hhplus_tdd_clean_architecture_java.application.lecture.LectureService;
import com.example.hhplus_tdd_clean_architecture_java.domain.lecture.Lecture;
import com.example.hhplus_tdd_clean_architecture_java.infrastructure.persistence.ApplicationRepository;
import com.example.hhplus_tdd_clean_architecture_java.infrastructure.persistence.LectureRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@SpringBootTest
class LectureServiceImplTest {
    @Autowired
    private LectureService lectureService;

    @MockBean
    private LectureRepository lectureRepository;

    @MockBean
    private ApplicationRepository applicationRepository;

    @Test
    public void apply_for_lecture_success() {
        // Given
        Lecture lecture = new Lecture("항해99", "허재님", LocalDate.of(2024, 01, 01));
        given(lectureRepository.findById(anyLong())).willReturn(Optional.of(lecture));
        given(applicationRepository.existsByUserIdAndLectureId(anyLong(), anyLong())).willReturn(false);

        // When
        boolean success = lectureService.applyForLecture(1L, 1L);
        assertTrue(success);
    }

    @Test
    public void apply_for_lecture_user_already_applied() {
        // Given
        Lecture lecture = new Lecture("항해99", "허재님", LocalDate.of(2024, 01, 01));
        given(lectureRepository.findById(anyLong())).willReturn(Optional.of(lecture));
        given(applicationRepository.existsByUserIdAndLectureId(anyLong(), anyLong())).willReturn(true); // 중복 신청

        // When & Then
        assertThrows(IllegalStateException.class, () -> {
            lectureService.applyForLecture(1L, 1L);
        });
    }

    @Test
    public void only_30_users_can_apply_for_lecture() {
        // Given
        Lecture lecture = new Lecture("항해99", "허재님", LocalDate.of(2024, 01, 01));
        given(lectureRepository.findById(anyLong())).willReturn(Optional.of(lecture));

        // 29명의 신청 상태 설정
        given(applicationRepository.countByLecture(lecture)).willReturn(29L);
        given(applicationRepository.existsByUserIdAndLectureId(anyLong(), anyLong())).willReturn(false);

        // When - 30번째 신청자는 성공
        boolean success = lectureService.applyForLecture(30L, 1L);
        assertTrue(success);

        // 30명의 신청 상태 설정
        given(applicationRepository.countByLecture(lecture)).willReturn(30L);

        // Then - 31번째 신청자는 실패
        assertThrows(IllegalStateException.class, () -> {
            lectureService.applyForLecture(31L, 1L);
        });
    }

    @Test
    public void get_all_lectures_returns_lectures() {
        // Given
        List<Lecture> lectures = Arrays.asList(
                new Lecture("자바", "허재님", LocalDate.of(2024, 01, 01)),
                new Lecture("코틀린", "허재님", LocalDate.of(2024, 01, 02))
        );
        given(lectureRepository.findAll()).willReturn(lectures);

        // When
        List<Lecture> result = lectureService.getAllLectures();

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("자바", result.get(0).getTitle());
        assertEquals("코틀린", result.get(1).getTitle());
    }
}
