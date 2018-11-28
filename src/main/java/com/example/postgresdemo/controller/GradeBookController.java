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

@RestController
public class GradeBookController {

    @Autowired
    private GradeBookRepository gradeBookRepository;

    @GetMapping("/api/gradeBook")
    public Page<GradeBook> getGradeBooks(Pageable pageable) {
        return gradeBookRepository.findAll(pageable);
    }


    @PostMapping("/api/gradeBook")
    public GradeBook createGradeBook(@Valid @RequestBody GradeBook gradeBook) {
        return gradeBookRepository.save(gradeBook);
    }

    @PutMapping("/api/gradeBook/{gradeBookId}")
    public GradeBook updateGradeBook(@PathVariable Long gradeBookId,
                                   @Valid @RequestBody GradeBook gradeBookRequest) {
        return gradeBookRepository.findById(gradeBookId)
                .map(gradeBook -> {
                    gradeBook.setSubject(gradeBookRequest.getSubject());
                    gradeBook.setName(gradeBookRequest.getName());
                    return gradeBookRepository.save(gradeBook);
                }).orElseThrow(() -> new ResourceNotFoundException("GradeBook not found with id " + gradeBookId));
    }


    @DeleteMapping("/api/gradeBook/{gradeBookId}")
    public ResponseEntity<?> deleteGradeBook(@PathVariable Long gradeBookId) {
        return gradeBookRepository.findById(gradeBookId)
                .map(gradeBook -> {
                    gradeBookRepository.delete(gradeBook);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("GradeBook not found with id " + gradeBookId));
    }
}
