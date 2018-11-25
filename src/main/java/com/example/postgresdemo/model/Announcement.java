package com.example.postgresdemo.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Table(name = "announcements")
public class Announcement extends AuditModel {
    @Id
    @GeneratedValue(generator = "announcement_generator")
    @SequenceGenerator(
            name = "announcement_generator",
            sequenceName = "announcement_sequence",
            initialValue = 1000
    )
    private Long id;

    private String title;

    private String content;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
