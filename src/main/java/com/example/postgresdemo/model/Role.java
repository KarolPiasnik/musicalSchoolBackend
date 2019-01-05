package com.example.postgresdemo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
@Entity
@Getter
@Setter
@ToString
@Data
@Table(name = "roles")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Role extends AuditModel {
    @Id
    @GeneratedValue(generator = "role_generator")
    @SequenceGenerator(
            name = "role_generator",
            sequenceName = "role_sequence",
            initialValue = 1000
    )
    private Long id;

    private String name;

    public Role(String name){
        this.name = name;
    }
}


