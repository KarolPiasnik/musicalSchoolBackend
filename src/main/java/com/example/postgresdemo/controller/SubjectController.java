package com.example.postgresdemo.controller;

import com.example.postgresdemo.exception.ResourceNotFoundException;
import com.example.postgresdemo.model.Subject;
import com.example.postgresdemo.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class SubjectController {

    @Autowired
    private SubjectRepository subjectRepository;

    @GetMapping("/api/subjects")
    public List<Subject> getSubjects(Pageable pageable) {
        return subjectRepository.findAll(pageable).getContent();
    }


    @PostMapping("/api/subjects")
    public Subject createSubject(@Valid @RequestBody Subject subject) {
        return subjectRepository.save(subject);
    }

    @PutMapping("/api/subjects/{subjectId}")
    public Subject updateSubject(@PathVariable Long subjectId,
                                 @Valid @RequestBody Subject subjectRequest) {
        return subjectRepository.findById(subjectId)
                .map(subject -> {
                    subject.setName(subjectRequest.getName());
                    subject.setDescription(subjectRequest.getDescription());
                    return subjectRepository.save(subject);
                }).orElseThrow(() -> new ResourceNotFoundException("Subject not found with id " + subjectId));
    }


    @DeleteMapping("/api/subjects/{subjectId}")
    public ResponseEntity<?> deleteSubject(@PathVariable Long subjectId) {
        return subjectRepository.findById(subjectId)
                .map(subject -> {
                    subjectRepository.delete(subject);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Subject not found with id " + subjectId));
    }
}
