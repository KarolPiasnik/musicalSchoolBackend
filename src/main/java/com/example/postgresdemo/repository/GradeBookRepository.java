package com.example.postgresdemo.repository;

import com.example.postgresdemo.model.GradeBook;
import com.example.postgresdemo.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GradeBookRepository extends JpaRepository<GradeBook, Long> {
    List<GradeBook> findBySubjectId(Long subjectId);
    List<Teacher> findByTeacherId(Long teacherId);

}
