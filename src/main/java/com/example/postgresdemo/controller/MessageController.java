package com.example.postgresdemo.controller;

import com.example.postgresdemo.exception.ResourceNotFoundException;
import com.example.postgresdemo.model.Message;
import com.example.postgresdemo.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class MessageController {
    @Autowired
    private MessageRepository messageRepository;

    @GetMapping("/api/messages")
    public List<Message> getMessages(Pageable pageable) {
        return messageRepository.findAll(pageable).getContent();
    }


    @PostMapping("/api/messages")
    public Message createMessage(@Valid @RequestBody Message message) {
        return messageRepository.save(message);
    }

    @PutMapping("/api/messages/{messageId}")
    public Message updateMessage(@PathVariable Long messageId,
                                           @Valid @RequestBody Message messageRequest) {
        return messageRepository.findById(messageId)
                .map(message -> {
                    message.setTitle(messageRequest.getTitle());
                    message.setContent(messageRequest.getContent());
                    return messageRepository.save(message);
                }).orElseThrow(() -> new ResourceNotFoundException("Message not found with id " + messageId));
    }


    @DeleteMapping("/api/messages/{messageId}")
    public ResponseEntity<?> deleteMessage(@PathVariable Long messageId) {
        return messageRepository.findById(messageId)
                .map(message -> {
                    messageRepository.delete(message);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Message not found with id " + messageId));
    }
}
