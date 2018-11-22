package com.example.postgresdemo.repository;

import com.example.postgresdemo.model.Question;
import com.example.postgresdemo.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
}
