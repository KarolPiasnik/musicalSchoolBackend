package com.example.postgresdemo.controller;

import com.example.postgresdemo.exception.ResourceNotFoundException;
import com.example.postgresdemo.model.Grade;
import com.example.postgresdemo.repository.GradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class GradeController {

    @Autowired
    private GradeRepository gradeRepository;

    @GetMapping("/api/grades")
    public List<Grade> getGrades(@PageableDefault(value = 50) Pageable pageable) {
        return gradeRepository.findAll(pageable).getContent();
    }


    @PostMapping("/api/grades")
    public Grade createGrade(@Valid @RequestBody Grade grade) {
        return gradeRepository.save(grade);
    }


    @PutMapping("/api/grades/{gradeId}")
    public Grade updateGrade(@PathVariable Long gradeId,
                                 @Valid @RequestBody Grade gradeRequest) {
        return gradeRepository.findById(gradeId)
                .map(grade -> {
                    return gradeRepository.save(grade);
                }).orElseThrow(() -> new ResourceNotFoundException("Grade not found with id " + gradeId));
    }


    @DeleteMapping("/api/grades/{gradeId}")
    public ResponseEntity<?> deleteGrade(@PathVariable Long gradeId) {
        return gradeRepository.findById(gradeId)
                .map(grade -> {
                    gradeRepository.delete(grade);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Grade not found with id " + gradeId));
    }
}
