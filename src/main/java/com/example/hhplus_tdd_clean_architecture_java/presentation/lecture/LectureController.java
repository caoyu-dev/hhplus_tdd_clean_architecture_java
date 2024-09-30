package com.example.hhplus_tdd_clean_architecture_java.presentation.lecture;

import com.example.hhplus_tdd_clean_architecture_java.application.lecture.LectureService;
import com.example.hhplus_tdd_clean_architecture_java.domain.lecture.Lecture;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/lectures")
public class LectureController {
    private final LectureService lectureService;

    public LectureController(LectureService lectureService) {
        this.lectureService = lectureService;
    }

    @PostMapping("/{lectureId}/apply")
    public ResponseEntity<String> applyForLecture(@PathVariable Long lectureId, @RequestBody Long userId) {
        try {
            lectureService.applyForLecture(userId, lectureId);
            return ResponseEntity.ok("Success");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Lecture>> getAllLectures() {
        List<Lecture> lectures = lectureService.getAllLectures();
        return ResponseEntity.ok(lectures);
    }
}
