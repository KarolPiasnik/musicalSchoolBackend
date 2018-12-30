package com.example.postgresdemo.controller;

import com.example.postgresdemo.exception.ResourceNotFoundException;
import com.example.postgresdemo.model.Remark;
import com.example.postgresdemo.repository.RemarkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class RemarkController {

    @Autowired
    private RemarkRepository remarkRepository;

    @GetMapping("/api/remarks")
    public List<Remark> getRemarks(@PageableDefault(value = 50) Pageable pageable) {
        return remarkRepository.findAll(pageable).getContent();
    }


    @PostMapping("/api/remarks")
    public Remark createRemark(@Valid @RequestBody Remark remark) {
        return remarkRepository.save(remark);
    }


    @PutMapping("/api/remarks/{remarkId}")
    public Remark updateRemark(@PathVariable Long remarkId,
                                 @Valid @RequestBody Remark remarkRequest) {
        return remarkRepository.findById(remarkId)
                .map(remark -> {
                    return remarkRepository.save(remark);
                }).orElseThrow(() -> new ResourceNotFoundException("Remark not found with id " + remarkId));
    }


    @DeleteMapping("/api/remarks/{remarkId}")
    public ResponseEntity<?> deleteRemark(@PathVariable Long remarkId) {
        return remarkRepository.findById(remarkId)
                .map(remark -> {
                    remarkRepository.delete(remark);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Remark not found with id " + remarkId));
    }
}
