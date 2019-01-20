package com.example.postgresdemo.controller;

import com.example.postgresdemo.exception.ResourceNotFoundException;
import com.example.postgresdemo.model.Message;
import com.example.postgresdemo.model.User;
import com.example.postgresdemo.model.User;
import com.example.postgresdemo.repository.MessageRepository;
import com.example.postgresdemo.repository.UserRepository;
import com.example.postgresdemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class MessageController {
    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/api/messages")
    public List<Message> getMessages(Pageable pageable) {
        return messageRepository.findAll(pageable).getContent();
    }

    @GetMapping("/api/messages/{username}")
    public List<Message> getMyMessages(@PathVariable String username) {
        User user = userRepository.findByUsername(username).get();
        List<User> users = new ArrayList<>();
        users.add(user);
        return messageRepository.getAllByReceiversIsContainingOrUserFrom(users, user);
    }


    @PostMapping("/api/messages")
    public Message createMessage(@Valid @RequestBody Message message) {
//        Message m = new Message();
//        m.setTitle(message.getTitle());
//        m.setContent(message.getTitle());
//        m.setReceivers(Arrays.asList(new User[]{userRepository.findById(message.getUserFrom().getId()).get()}));
//        m.setUserFrom(userRepository.findById(message.getUserFrom().getId()).get());
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



