package com.example.postgresdemo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "message")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Message extends AuditModel {
    @Id
    @GeneratedValue(generator = "message_generator")
    @SequenceGenerator(
            name = "message_generator",
            sequenceName = "message_sequence",
            initialValue = 1000
    )
    private Long id;

    private String title;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User userFrom;

    @ManyToMany(cascade = {CascadeType.MERGE})
    @JoinTable(
            name = "message_user",
            joinColumns = {@JoinColumn(name = "message_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")}
    )
    @JsonIgnoreProperties("gradebooks")
    private List<User> receivers;
}



















