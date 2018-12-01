package com.example.postgresdemo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "gradebooks")
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

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "Gradebook_Student",
            joinColumns = { @JoinColumn(name = "gradebook_id") },
            inverseJoinColumns = { @JoinColumn(name = "student_id") }
    )
    @JsonIgnoreProperties("gradebooks")
    private List<Student> students;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "teacher_id", nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnoreProperties("gradebooks")
    private Teacher teacher;

    public Long getId() {

        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }




}
