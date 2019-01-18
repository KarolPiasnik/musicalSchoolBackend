package com.example.postgresdemo.controller;

import com.example.postgresdemo.exception.ResourceNotFoundException;
import com.example.postgresdemo.model.Teacher;
import com.example.postgresdemo.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class TeacherController {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private TeacherRepository teacherRepository;

    @GetMapping("/api/teachers")
    public List<Teacher> getTeachers(@PageableDefault(value = 50) Pageable pageable) {
        return teacherRepository.findAll(pageable).getContent();
    }


    @PostMapping("/api/teachers")
    public Teacher createTeacher(@Valid @RequestBody Teacher teacher) {
        teacher.setPassword(passwordEncoder.encode(teacher.getPassword()));
        return teacherRepository.save(teacher);
    }


    @PutMapping("/api/teachers/{teacherId}")
    public Teacher updateTeacher(@PathVariable Long teacherId,
                                 @Valid @RequestBody Teacher teacherRequest) {
        return teacherRepository.findById(teacherId).orElseThrow(() -> new ResourceNotFoundException("Teacher not found with id " + teacherId));
    }


    @DeleteMapping("/api/teachers/{teacherId}")
    public ResponseEntity<?> deleteTeacher(@PathVariable Long teacherId) {
        return teacherRepository.findById(teacherId)
                .map(teacher -> {
                    teacherRepository.delete(teacher);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Teacher not found with id " + teacherId));
    }
}
