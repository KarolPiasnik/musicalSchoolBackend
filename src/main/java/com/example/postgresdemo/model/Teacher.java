package com.example.postgresdemo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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

    @NotNull
    private String pesel;

    @NotNull
    private String name;

    @NotNull
    private String surname;

    @NotNull
    private String address;

//    @NotNull
//    private Boolean active;

    @OneToMany(mappedBy = "teacher")
    @JsonIgnoreProperties("teacher")
    private List<Gradebook> gradebooks;
}
