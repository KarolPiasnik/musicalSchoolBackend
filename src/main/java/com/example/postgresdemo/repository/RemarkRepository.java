package com.example.postgresdemo.repository;

import com.example.postgresdemo.model.Remark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RemarkRepository extends JpaRepository<Remark, Long> {
    //List<Student> findByGradebookId(Long gradebooksId);

}
