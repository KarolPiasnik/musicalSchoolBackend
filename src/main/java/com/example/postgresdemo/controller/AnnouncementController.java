package com.example.postgresdemo.controller;

import com.example.postgresdemo.exception.ResourceNotFoundException;
import com.example.postgresdemo.model.Announcement;
import com.example.postgresdemo.repository.AnnouncementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class AnnouncementController {

    @Autowired
    private AnnouncementRepository announcementRepository;

    @GetMapping("/api/announcements")
    public List<Announcement> getAnnouncements(Pageable pageable) {
        return announcementRepository.findAll(pageable).getContent();
    }


    @PostMapping("/api/announcements")
    public Announcement createAnnouncement(@Valid @RequestBody Announcement announcement) {
        return announcementRepository.save(announcement);
    }

    @PutMapping("/api/announcements/{announcementId}")
    public Announcement updateAnnouncement(@PathVariable Long announcementId,
                                   @Valid @RequestBody Announcement announcementRequest) {
        return announcementRepository.findById(announcementId)
                .map(announcement -> {
                    announcement.setTitle(announcementRequest.getTitle());
                    announcement.setContent(announcementRequest.getContent());
                    return announcementRepository.save(announcement);
                }).orElseThrow(() -> new ResourceNotFoundException("Announcement not found with id " + announcementId));
    }


    @DeleteMapping("/api/announcements/{announcementId}")
    public ResponseEntity<?> deleteAnnouncement(@PathVariable Long announcementId) {
        return announcementRepository.findById(announcementId)
                .map(announcement -> {
                    announcementRepository.delete(announcement);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Announcement not found with id " + announcementId));
    }
}
