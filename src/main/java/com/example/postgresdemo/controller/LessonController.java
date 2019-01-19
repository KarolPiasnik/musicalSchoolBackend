package com.example.postgresdemo.controller;

import com.example.postgresdemo.exception.ResourceNotFoundException;
import com.example.postgresdemo.model.Lesson;
import com.example.postgresdemo.model.Student;
import com.example.postgresdemo.repository.LessonRepository;
import com.example.postgresdemo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

@RestController
public class LessonController {

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private StudentRepository studentRepository;

    @GetMapping("/api/lessons")
    public List<Lesson> getLessons(@PageableDefault(value = 50) Pageable pageable) {
        return lessonRepository.findAll(pageable).getContent();
    }

    @GetMapping("/api/lessons/{weekStartDate}/{weekEndDate}")
    public List<Lesson> getLessonsByWeek(@PathVariable Long weekStartDate, @PathVariable Long weekEndDate) {

        Date weekStart = new Date(new Timestamp(weekStartDate).getTime());
        Date weekEnd = new Date(new Timestamp(weekEndDate).getTime());

        return lessonRepository.findAllByStartTimeIsGreaterThanEqualAndEndTimeLessThanEqual(weekStart, weekEnd);
    }

    @GetMapping("/api/lessons/{weekStartDate}/{weekEndDate}/{name}")
    public List<Lesson> getLessonsByWeekAndName(@PathVariable Long weekStartDate, @PathVariable Long weekEndDate, @PathVariable String name) {

        Date weekStart = new Date(new Timestamp(weekStartDate).getTime());
        Date weekEnd = new Date(new Timestamp(weekEndDate).getTime());

        Student student = studentRepository.findByName(name).get();
        List<Student> students = new ArrayList<>();
        students.add(student);

        return lessonRepository.findAllByStartTimeIsGreaterThanEqualAndEndTimeLessThanEqualAndStudentsIn(weekStart, weekEnd, students);
    }


    @PostMapping("/api/lessons")
    public Lesson createLesson(@Valid @RequestBody Lesson lesson) {
        return lessonRepository.save(lesson);
    }


    @PutMapping("/api/lessons/{lessonId}")
    public Lesson updateLesson(@PathVariable Long lessonId,
                                 @Valid @RequestBody Lesson lessonRequest) {
        return lessonRepository.findById(lessonId)
                .map(lesson -> {
                    return lessonRepository.save(lessonRequest);
                }).orElseThrow(() -> new ResourceNotFoundException("Lesson not found with id " + lessonId));
    }


    @DeleteMapping("/api/lessons/{lessonId}")
    public ResponseEntity<?> deleteLesson(@PathVariable Long lessonId) {
        return lessonRepository.findById(lessonId)
                .map(lesson -> {
                    lessonRepository.delete(lesson);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Lesson not found with id " + lessonId));
    }
}
