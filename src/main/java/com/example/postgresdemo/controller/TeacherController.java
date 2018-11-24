package com.example.postgresdemo.controller;

import com.example.postgresdemo.exception.ResourceNotFoundException;
import com.example.postgresdemo.model.Teacher;
import com.example.postgresdemo.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class TeacherController {

    @Autowired
    private TeacherRepository teacherRepository;

    @GetMapping("/teachers")
    public List<Teacher> getTeachers(@PageableDefault(value=50) Pageable pageable) {
        return teacherRepository.findAll(pageable).getContent();
    }


    @PostMapping("/teachers")
    public Teacher createTeacher( @Valid @RequestBody Teacher teacher) {
        System.out.println(teacher.getName());
        System.out.println(teacher.getAddress());
        System.out.println(teacher.getSurname());
        System.out.println(teacher.getPesel());
       // System.out.println(teacher.getActive());
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
