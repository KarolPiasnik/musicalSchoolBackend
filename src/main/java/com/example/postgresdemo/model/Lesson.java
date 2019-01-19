package com.example.postgresdemo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "lessons")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Lesson extends AuditModel {
    @Id
    @GeneratedValue(generator = "lesson_generator")
    @SequenceGenerator(
            name = "lesson_generator",
            sequenceName = "lesson_sequence",
            initialValue = 1000
    )
    private Long id;

    @ManyToMany(cascade = {CascadeType.MERGE})
    @JoinTable(
            name = "lesson_student",
            joinColumns = {@JoinColumn(name = "lesson_id")},
            inverseJoinColumns = {@JoinColumn(name = "student_id")}
    )
    @JsonIgnoreProperties(value = {"lessons"}, allowSetters = true)
    private List<Student> students;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "gradebook_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnoreProperties(value = {"lessons"}, allowSetters = false)
    private Gradebook gradebook;

    private Date startTime;

    private Date endTime;

    private Boolean present;
}


