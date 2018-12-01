package com.example.postgresdemo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "subjects")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Subject extends AuditModel {
    @Id
    @GeneratedValue(generator = "subject_generator")
    @SequenceGenerator(
            name = "subject_generator",
            sequenceName = "subject_sequence",
            initialValue = 1000
    )
    private Long id;

    @OneToMany(mappedBy = "subject")
    @JsonIgnoreProperties("subject")
    private List<Gradebook> gradebooks;

    private String name;

    private String description;
}


