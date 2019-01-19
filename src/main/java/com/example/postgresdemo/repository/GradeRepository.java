package com.example.postgresdemo.repository;

import com.example.postgresdemo.model.Grade;
import com.example.postgresdemo.model.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Long> {
    List<Grade> findAllByStudentId(Long studentId);
}
