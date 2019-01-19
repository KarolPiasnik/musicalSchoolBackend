package com.example.postgresdemo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/***
 * Define authentication properties associated with a user.
 */
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@Table(name = "users")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User extends AuditModel implements Serializable {

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

    private Boolean active = true;

    private String pesel;

    private String name;

    private String surname;

    private String address;


    public User(String username, String password, List<Role> roles, Boolean active, String pesel,  String name, String surname, String address) {
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.active = active;
        this.address = address;
        this.name = name;
        this.surname = surname;
        this.pesel = pesel;
    }

}
