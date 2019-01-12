package com.example.postgresdemo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/***
 * Define authentication properties associated with a user.
 */

@Entity
@Data
@Table(name = "users")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User implements Serializable {

    @Id
    @GeneratedValue
    @SequenceGenerator(
            name = "user_generator",
            sequenceName = "user_sequence",
            initialValue = 1000
    )
    private Long id;

    @Column(unique=true)
    private String username;
    private String password;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Role> roles;
    private Boolean active;

    public User(){}

    public User(String username, String password, List<Role> roles, Boolean active) {
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.active = active;
    }

}
