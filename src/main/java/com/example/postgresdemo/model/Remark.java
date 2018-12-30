package com.example.postgresdemo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import javax.persistence.Id;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "remarks")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Remark extends AuditModel {

    @Id
    @GeneratedValue(generator = "remark_generator")
    @SequenceGenerator(
            name = "remark_generator",
            sequenceName = "remark_sequence",
            initialValue = 1000
    )
    private Long id;

    String content;

}


