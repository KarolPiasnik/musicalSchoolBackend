package com.example.postgresdemo.repository;

import com.example.postgresdemo.model.Gradebook;
import com.example.postgresdemo.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GradebookRepository extends JpaRepository<Gradebook, Long> {
    List<Gradebook> findBySubjectId(Long subjectId);

    List<Teacher> findByTeacherId(Long teacherId);

}
