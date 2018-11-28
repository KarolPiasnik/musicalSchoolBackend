package com.example.postgresdemo.controller;

import com.example.postgresdemo.exception.ResourceNotFoundException;
import com.example.postgresdemo.model.Student;
import com.example.postgresdemo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @GetMapping("/api/students")
    public Page<Student> getStudents(Pageable pageable) {
        return studentRepository.findAll(pageable);
    }


    @PostMapping("/api/students")
    public Student createStudent(@Valid @RequestBody Student student) {
        return studentRepository.save(student);
    }

    @PutMapping("/api/students/{studentId}")
    public Student updateStudent(@PathVariable Long studentId,
                                   @Valid @RequestBody Student studentRequest) {
        return studentRepository.findById(studentId)
                .map(student -> {
                    student.setName(studentRequest.getName());
                    student.setSurname(studentRequest.getSurname());
                    return studentRepository.save(student);
                }).orElseThrow(() -> new ResourceNotFoundException("Student not found with id " + studentId));
    }


    @DeleteMapping("/api/students/{studentId}")
    public ResponseEntity<?> deleteStudent(@PathVariable Long studentId) {
        return studentRepository.findById(studentId)
                .map(student -> {
                    studentRepository.delete(student);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Student not found with id " + studentId));
    }
}
