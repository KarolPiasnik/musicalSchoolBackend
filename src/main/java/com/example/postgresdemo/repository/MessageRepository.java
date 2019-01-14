package com.example.postgresdemo.repository;

import com.example.postgresdemo.model.Message;
import com.example.postgresdemo.model.Student;
import com.example.postgresdemo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> getAllByReceiversIsContainingOrStudentFrom(List<Student> receivers, Student studentFrom);
}
