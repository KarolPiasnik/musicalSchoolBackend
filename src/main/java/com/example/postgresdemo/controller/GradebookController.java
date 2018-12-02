package com.example.postgresdemo.controller;

import com.example.postgresdemo.exception.ResourceNotFoundException;
import com.example.postgresdemo.model.Gradebook;
import com.example.postgresdemo.model.Student;
import com.example.postgresdemo.model.Subject;
import com.example.postgresdemo.model.Teacher;
import com.example.postgresdemo.repository.GradebookRepository;
import com.example.postgresdemo.repository.StudentRepository;
import com.example.postgresdemo.repository.SubjectRepository;
import com.example.postgresdemo.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
public class GradebookController {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    private GradebookRepository gradebookRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private SubjectRepository subjectRepository;


    @GetMapping("/api/gradebooks")
    public List<Gradebook> getGradebooks(Pageable pageable) {
        return gradebookRepository.findAll(pageable).getContent();
    }

    @GetMapping("/api/gradebooks/{gradebookId}")
    public Gradebook getGradebook(@PathVariable Long gradebookId) {
        return gradebookRepository.findById(gradebookId).get();
    }

    @PostMapping("/api/gradebooks/{gradebookId}/students")
    public Gradebook addStudentToGradebook(@PathVariable Long gradebookId, @Valid @RequestBody Student student) {
        Gradebook gradebook = gradebookRepository.findById(gradebookId).get();
        if (gradebook != null) {
            if (student != null) {
                if (student.getId() != null) {
                    Optional<Student> studentToAdd = studentRepository.findById(student.getId());
                    if (studentToAdd.isPresent()) {
                        List<Student> students = new LinkedList<>();
                        students = gradebook.getStudents();
                        if(students.contains(studentToAdd.get())){
                            throw new DuplicateKeyException("Nie można dodać 2 razy tego samego studenta");
                        }
                        students.add(studentToAdd.get());

                        gradebook.setStudents(students);
                        return gradebookRepository.save(gradebook);
                    }
                    else {
                        throw new NoSuchElementException("No student with such ID in database");
                    }
                } else {
                    throw new NoSuchElementException("Provided student has no ID");
                }
            } else {
                throw new NoSuchElementException("No student in request");
            }
        } else {
            throw new NoSuchElementException("gradebook");
        }
    }

    @PostMapping("/api/gradebooks")
    public Gradebook createGradebook(@Valid @RequestBody Gradebook gradebook) {
        if (gradebook.getSubject().getId() != null) {
            Subject subject = subjectRepository.getOne(gradebook.getSubject().getId());
            if (subject == null) {
                throw new NoSuchElementException();
            }
            subjectRepository.save(subject);
        } else {
            subjectRepository.save(gradebook.getSubject());
        }


        if (gradebook.getTeacher().getId() != null) {
            Teacher teacher = teacherRepository.getOne(gradebook.getTeacher().getId());
            if (teacher == null) {
                throw new NoSuchElementException();
            }
            teacherRepository.save(teacher);
        } else {
            teacherRepository.save(gradebook.getTeacher());

        }

        return gradebookRepository.save(gradebook);
    }

    @PutMapping("/api/gradebooks/{gradebookId}")
    public Gradebook updateGradebook(@PathVariable Long gradebookId,
                                     @Valid @RequestBody Gradebook gradebookRequest) {
        return gradebookRepository.findById(gradebookId)
                .map(gradebook -> {
                    gradebook.setSubject(gradebookRequest.getSubject());
                    gradebook.setName(gradebookRequest.getName());
                    gradebook.setTeacher(gradebookRequest.getTeacher());
                    return gradebookRepository.save(gradebook);
                }).orElseThrow(() -> new ResourceNotFoundException("Gradebook not found with id " + gradebookId));
    }


    @DeleteMapping("/api/gradebooks/{gradebookId}")
    public ResponseEntity<?> deleteGradebook(@PathVariable Long gradebookId) {
        return gradebookRepository.findById(gradebookId)
                .map(gradebook -> {
                    gradebookRepository.delete(gradebook);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Gradebook not found with id " + gradebookId));
    }
}
