package com.example.postgresdemo.repository;

import com.example.postgresdemo.model.Lesson;
import com.example.postgresdemo.model.Student;
import com.example.postgresdemo.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {
    List<Lesson> findAllByStartTimeIsGreaterThanEqualAndEndTimeLessThanEqual(Date startTime, Date endTime);

    List<Lesson> findAllByStartTimeIsGreaterThanEqualAndEndTimeLessThanEqualAndStudentsIn(Date startTime, Date endTime, List<Student> students);
}
