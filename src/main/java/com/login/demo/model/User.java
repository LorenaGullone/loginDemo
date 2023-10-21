package com.login.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
@Table(name= "user", schema = "login")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private long id;

    @Basic
    @Column(name = "username", unique = true, length = 10, nullable = false)
    @NotNull
    private String username;

    @Basic
    @Column(name = "password", length = 10, nullable = false)
    @NotNull
    private String password;

}//User
