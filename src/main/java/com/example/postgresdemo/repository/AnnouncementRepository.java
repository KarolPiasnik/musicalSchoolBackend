package com.example.postgresdemo.repository;

import com.example.postgresdemo.model.Announcement;
import com.example.postgresdemo.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
}
