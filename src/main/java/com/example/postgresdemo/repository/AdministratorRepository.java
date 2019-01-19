package com.example.postgresdemo.repository;

import com.example.postgresdemo.model.Administrator;
import com.example.postgresdemo.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdministratorRepository extends JpaRepository<Administrator, Long> {
}
