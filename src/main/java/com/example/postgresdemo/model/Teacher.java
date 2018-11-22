package com.example.postgresdemo.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "teachers")
public class Teacher extends Person {
    @Id
    @GeneratedValue(generator = "teacher_generator")
    @SequenceGenerator(
            name = "teacher_generator",
            sequenceName = "teacher_sequence",
            initialValue = 1000
    )
    private Long id;

    @OneToMany(mappedBy = "teacher")
    private List<Register> registers;
}
