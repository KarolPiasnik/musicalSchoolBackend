package com.example.postgresdemo.controller;

import com.example.postgresdemo.exception.ResourceNotFoundException;
import com.example.postgresdemo.model.GradeBook;
import com.example.postgresdemo.repository.GradeBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class GradeBookController {

    @Autowired
    private GradeBookRepository gradeBookRepository;

    @GetMapping("/api/gradebooks")
    public List<GradeBook> getGradeBooks(Pageable pageable) {
        return gradeBookRepository.findAll(pageable).getContent();
    }

    @GetMapping("/api/gradebooks/{gradeBookId}")
    public GradeBook getGradeBook(@PathVariable Long gradeBookId) {
        return gradeBookRepository.findById(gradeBookId).get();
    }


    @PostMapping("/api/gradebooks")
    public GradeBook createGradeBook(@Valid @RequestBody GradeBook gradeBook) {
        return gradeBookRepository.save(gradeBook);
    }

    @PutMapping("/api/gradebooks/{gradeBookId}")
    public GradeBook updateGradeBook(@PathVariable Long gradeBookId,
                                   @Valid @RequestBody GradeBook gradeBookRequest) {
        return gradeBookRepository.findById(gradeBookId)
                .map(gradeBook -> {
                    gradeBook.setSubject(gradeBookRequest.getSubject());
                    gradeBook.setName(gradeBookRequest.getName());
                    gradeBook.setTeacher(gradeBookRequest.getTeacher());
                    return gradeBookRepository.save(gradeBook);
                }).orElseThrow(() -> new ResourceNotFoundException("GradeBook not found with id " + gradeBookId));
    }


    @DeleteMapping("/api/gradebooks/{gradeBookId}")
    public ResponseEntity<?> deleteGradeBook(@PathVariable Long gradeBookId) {
        return gradeBookRepository.findById(gradeBookId)
                .map(gradeBook -> {
                    gradeBookRepository.delete(gradeBook);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("GradeBook not found with id " + gradeBookId));
    }
}
