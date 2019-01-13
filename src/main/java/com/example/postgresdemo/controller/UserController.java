package com.example.postgresdemo.controller;

import com.example.postgresdemo.exception.ResourceNotFoundException;
import com.example.postgresdemo.model.User;
import com.example.postgresdemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @GetMapping("/api/users")
    public List<User> getUsers(Pageable pageable) {
        return userRepository.findAll(pageable).getContent();
    }

    @GetMapping("/api/user/{username}")
    public User getUserByName(@PathVariable String username) {
        return userRepository.findByUsername(username).get();
    }

    @PostMapping("/api/users")
    public User createUser(@Valid @RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        if (user.getActive() == null) {
//            user.setActive(false);
//        }
        return userRepository.save(user);
    }

    @PutMapping("/api/users/{userId}")
    public User updateUser(@PathVariable Long userId,
                           @Valid @RequestBody User userRequest) {
        return userRepository.findById(userId)
                .map(user -> {
                    return userRepository.save(user);
                }).orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));
    }


    @DeleteMapping("/api/users/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
        return userRepository.findById(userId)
                .map(user -> {
                    userRepository.delete(user);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));
    }
}
