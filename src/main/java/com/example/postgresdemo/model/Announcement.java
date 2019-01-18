package com.example.postgresdemo.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
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

    @Lob
    @Column(name = "content", length = 1000)
    private String content;

//
//    List<Role> roles;
}
