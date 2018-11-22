package com.example.postgresdemo.controller;

import com.example.postgresdemo.exception.ResourceNotFoundException;
import com.example.postgresdemo.model.Teacher;
import com.example.postgresdemo.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class TeacherController {

    @Autowired
    private TeacherRepository teacherRepository;

    @GetMapping("/teachers")
    public Page<Teacher> getTeachers(Pageable pageable) {
        return teacherRepository.findAll(pageable);
    }


    @PostMapping("/teachers")
    public Teacher createTeacher(@Valid @RequestBody Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    @PutMapping("/teachers/{teacherId}")
    public Teacher updateTeacher(@PathVariable Long teacherId,
                                   @Valid @RequestBody Teacher teacherRequest) {
        return teacherRepository.findById(teacherId)
                .map(teacher -> {
                    teacher.setName(teacherRequest.getName());
                    teacher.setSurname(teacherRequest.getSurname());
                    return teacherRepository.save(teacher);
                }).orElseThrow(() -> new ResourceNotFoundException("Teacher not found with id " + teacherId));
    }


    @DeleteMapping("/teachers/{teacherId}")
    public ResponseEntity<?> deleteTeacher(@PathVariable Long teacherId) {
        return teacherRepository.findById(teacherId)
                .map(teacher -> {
                    teacherRepository.delete(teacher);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Teacher not found with id " + teacherId));
    }
}
