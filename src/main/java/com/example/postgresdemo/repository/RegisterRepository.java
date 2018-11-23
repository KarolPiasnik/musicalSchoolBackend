package com.example.postgresdemo.repository;

import com.example.postgresdemo.model.Question;
import com.example.postgresdemo.model.Register;
import com.example.postgresdemo.model.Student;
import com.example.postgresdemo.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegisterRepository extends JpaRepository<Register, Long> {
    List<Register> findBySubjectId(Long subjectId);
    List<Teacher> findByTeacherId(Long teacherId);

}
