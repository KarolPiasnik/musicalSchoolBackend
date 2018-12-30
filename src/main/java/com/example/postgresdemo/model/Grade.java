package com.example.postgresdemo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "grades")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Grade extends AuditModel {
    @Id
    @GeneratedValue(generator = "grade_generator")
    @SequenceGenerator(
            name = "grade_generator",
            sequenceName = "grade_sequence",
            initialValue = 1000
    )
    private Long id;
//
////    @ManyToMany(cascade = {CascadeType.ALL})
////    @JoinTable(
////            name = "Lesson_Student",
////            joinColumns = {@JoinColumn(name = "lesson_id")},
////            inverseJoinColumns = {@JoinColumn(name = "student_id")}
////    )
////    @JsonIgnoreProperties("lessons")
////    private List<Student> students;
//
//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "gradebook_id", nullable = false)
//    @OnDelete(action = OnDeleteAction.CASCADE)
//    @JsonIgnoreProperties("lessons")
//    private Gradebook gradebook;
//
//    private Date from;
//
//    private Date to;
}


