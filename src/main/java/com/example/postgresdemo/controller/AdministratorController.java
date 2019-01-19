package com.example.postgresdemo.controller;

import com.example.postgresdemo.exception.ResourceNotFoundException;
import com.example.postgresdemo.model.Administrator;
import com.example.postgresdemo.repository.AdministratorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class AdministratorController {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private AdministratorRepository administratorRepository;

    @GetMapping("/api/administrators")
    public List<Administrator> getAdministrators(@PageableDefault(value = 50) Pageable pageable) {
        return administratorRepository.findAll(pageable).getContent();
    }


    @PostMapping("/api/administrators")
    public Administrator createAdministrator(@Valid @RequestBody Administrator administrator) {
        administrator.setPassword(passwordEncoder.encode(administrator.getPassword()));
        return administratorRepository.save(administrator);
    }


    @PutMapping("/api/administrators/{administratorId}")
    public Administrator updateAdministrator(@PathVariable Long administratorId,
                                 @Valid @RequestBody Administrator administratorRequest) {
        return administratorRepository.findById(administratorId).orElseThrow(() -> new ResourceNotFoundException("Administrator not found with id " + administratorId));
    }


    @DeleteMapping("/api/administrators/{administratorId}")
    public ResponseEntity<?> deleteAdministrator(@PathVariable Long administratorId) {
        return administratorRepository.findById(administratorId)
                .map(administrator -> {
                    administratorRepository.delete(administrator);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Administrator not found with id " + administratorId));
    }
}
