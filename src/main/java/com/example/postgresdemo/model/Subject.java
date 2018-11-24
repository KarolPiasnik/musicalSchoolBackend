package com.example.postgresdemo.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="subjects")
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
    private List<GradeBook> gradeBooks;

    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<GradeBook> getGradeBooks() {
        return gradeBooks;
    }

    public void setGradeBooks(List<GradeBook> gradeBooks) {
        this.gradeBooks = gradeBooks;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private String description;


}


