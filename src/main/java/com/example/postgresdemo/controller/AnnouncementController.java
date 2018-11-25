package com.example.postgresdemo.controller;

import com.example.postgresdemo.exception.ResourceNotFoundException;
import com.example.postgresdemo.model.Announcement;
import com.example.postgresdemo.repository.AnnouncementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class AnnouncementController {

    @Autowired
    private AnnouncementRepository announcementRepository;

    @GetMapping("/announcements")
    public Page<Announcement> getAnnouncements(Pageable pageable) {
        return announcementRepository.findAll(pageable);
    }


    @PostMapping("/announcements")
    public Announcement createAnnouncement(@Valid @RequestBody Announcement announcement) {
        return announcementRepository.save(announcement);
    }

    @PutMapping("/announcements/{announcementId}")
    public Announcement updateAnnouncement(@PathVariable Long announcementId,
                                   @Valid @RequestBody Announcement announcementRequest) {
        return announcementRepository.findById(announcementId)
                .map(announcement -> {
                    announcement.setTitle(announcementRequest.getTitle());
                    announcement.setContent(announcementRequest.getContent());
                    return announcementRepository.save(announcement);
                }).orElseThrow(() -> new ResourceNotFoundException("Announcement not found with id " + announcementId));
    }


    @DeleteMapping("/announcements/{announcementId}")
    public ResponseEntity<?> deleteAnnouncement(@PathVariable Long announcementId) {
        return announcementRepository.findById(announcementId)
                .map(announcement -> {
                    announcementRepository.delete(announcement);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Announcement not found with id " + announcementId));
    }
}
