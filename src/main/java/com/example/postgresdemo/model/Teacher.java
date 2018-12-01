package com.example.postgresdemo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@Entity
@Table(name = "teachers")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Teacher extends AuditModel {
    @Id
    @GeneratedValue(generator = "teacher_generator")
    @SequenceGenerator(
            name = "teacher_generator",
            sequenceName = "teacher_sequence",
            initialValue = 1000
    )
    private Long id;

    @NotBlank
    private String pesel;

    @NotBlank
    private String name;

    @NotBlank
    private String surname;

    @NotBlank
    private String address;

//    @NotNull
//    private Boolean active;

    @OneToMany(mappedBy = "teacher")
    @JsonIgnoreProperties("teacher")
    private List<Gradebook> gradebooks;
}
