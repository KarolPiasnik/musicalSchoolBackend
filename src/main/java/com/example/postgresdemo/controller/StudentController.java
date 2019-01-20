package com.example.postgresdemo.controller;

import com.example.postgresdemo.exception.ResourceNotFoundException;
import com.example.postgresdemo.model.Grade;
import com.example.postgresdemo.model.Gradebook;
import com.example.postgresdemo.model.Student;
import com.example.postgresdemo.model.Subject;
import com.example.postgresdemo.repository.GradeRepository;
import com.example.postgresdemo.repository.GradebookRepository;
import com.example.postgresdemo.repository.LessonRepository;
import com.example.postgresdemo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class StudentController {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private GradeRepository gradeRepository;

    @Autowired
    private GradebookRepository gradebookRepository;

    @GetMapping("/api/students")
    public List<Student> getStudents(Pageable pageable) {
        return studentRepository.findAll(pageable).getContent();
    }


    @PostMapping("/api/students")
    public Student createStudent(@Valid @RequestBody Student student) {
        student.setPassword(passwordEncoder.encode(student.getPassword()));
        if (student.getMainGradebook() != null) {
            if (student.getMainGradebook().getId() != null) {
                Gradebook gradebook = gradebookRepository.getOne(student.getMainGradebook().getId());
                if (gradebook == null) {
                    throw new NoSuchElementException();
                }
                gradebookRepository.save(gradebook);
            } else {
                gradebookRepository.save(student.getMainGradebook());
            }
        }
        return studentRepository.save(student);
    }

    @PutMapping("/api/students/{studentId}")
    public Student updateStudent(@PathVariable Long studentId,
                                 @Valid @RequestBody Student studentRequest) {
        return studentRepository.findById(studentId)
                .map(student -> {
                    student.setName(studentRequest.getName());
                    student.setSurname(studentRequest.getSurname());
                    student.setUsername(studentRequest.getUsername());
                    student.setPesel(studentRequest.getPesel());
                    student.setAddress(studentRequest.getAddress());
                    return studentRepository.save(student);
                }).orElseThrow(() -> new ResourceNotFoundException("Student not found with id " + studentId));
    }

    @GetMapping("/api/students/{studentId}/grades")
    public List<Grade> updateStudent(@PathVariable Long studentId) {
        return gradeRepository.findAllByStudentId(studentId);
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
