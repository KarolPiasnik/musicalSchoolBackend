package com.example.postgresdemo.repository;

import com.example.postgresdemo.model.Question;
import com.example.postgresdemo.model.Register;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegisterRepository extends JpaRepository<Register, Long> {
}
