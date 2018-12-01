package com.example.postgresdemo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "students")
public class Student extends Person {
    @Id
    @GeneratedValue(generator = "student_generator")
    @SequenceGenerator(
            name = "student_generator",
            sequenceName = "student_sequence",
            initialValue = 1000
    )

    private Long id;

    @OneToMany(mappedBy = "students")
    @JsonIgnoreProperties("students")
    private Gradebook mainGradebook;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnoreProperties("students")
    private List<Gradebook> gradebooks;

}
