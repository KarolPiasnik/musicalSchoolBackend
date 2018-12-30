package com.example.postgresdemo.repository;

import com.example.postgresdemo.model.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Long> {
    //List<Student> findByGradebookId(Long gradebooksId);

}
