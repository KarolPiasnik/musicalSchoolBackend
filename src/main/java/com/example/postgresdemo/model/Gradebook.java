package com.example.postgresdemo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "gradebooks")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Gradebook extends AuditModel {
    @Id
    @GeneratedValue(generator = "gradebook_generator")
    @SequenceGenerator(
            name = "gradebook_generator",
            sequenceName = "gradebook_sequence",
            initialValue = 1000
    )
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "subject_id", nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnoreProperties("gradebooks")
    private Subject subject;

    private String name;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "gradebook_student",
            joinColumns = {@JoinColumn(name = "gradebook_id")},
            inverseJoinColumns = {@JoinColumn(name = "student_id")}
    )
    @JsonIgnoreProperties("gradebooks")
    private List<Student> students;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "teacher_id", nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnoreProperties("gradebooks")
    private Teacher teacher;

    @OneToMany(mappedBy = "gradebook")
    @JsonIgnoreProperties("gradebook")
    private List<Lesson> lessons;

    @OneToMany(mappedBy = "gradebook")
    @JsonIgnoreProperties("gradebook")
    private List<Grade> grades;

}
