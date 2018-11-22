package com.example.postgresdemo.controller;

import com.example.postgresdemo.exception.ResourceNotFoundException;
import com.example.postgresdemo.model.Register;
import com.example.postgresdemo.repository.RegisterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class RegisterController {

    @Autowired
    private RegisterRepository registerRepository;

    @GetMapping("/registers")
    public Page<Register> getRegisters(Pageable pageable) {
        return registerRepository.findAll(pageable);
    }


    @PostMapping("/registers")
    public Register createRegister(@Valid @RequestBody Register register) {
        return registerRepository.save(register);
    }

    @PutMapping("/registers/{registerId}")
    public Register updateRegister(@PathVariable Long registerId,
                                   @Valid @RequestBody Register registerRequest) {
        return registerRepository.findById(registerId)
                .map(register -> {
                    register.setSubject(registerRequest.getSubject());
                    register.setName(registerRequest.getName());
                    return registerRepository.save(register);
                }).orElseThrow(() -> new ResourceNotFoundException("Register not found with id " + registerId));
    }


    @DeleteMapping("/registers/{registerId}")
    public ResponseEntity<?> deleteRegister(@PathVariable Long registerId) {
        return registerRepository.findById(registerId)
                .map(register -> {
                    registerRepository.delete(register);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Register not found with id " + registerId));
    }
}
