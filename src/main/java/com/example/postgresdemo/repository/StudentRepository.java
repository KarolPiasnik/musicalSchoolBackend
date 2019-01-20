package com.example.postgresdemo.repository;

import com.example.postgresdemo.model.Student;
import com.example.postgresdemo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    //List<Student> findByGradebookId(Long gradebooksId);
    Optional<Student> findByName(String name);
}
