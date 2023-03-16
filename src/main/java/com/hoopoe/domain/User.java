package com.hoopoe.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Table(name = "t_user")
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 50, nullable = false)
    private String firstName;
    @Column(length = 50, nullable = false)
    private String lastName;
    @Column(length = 85, nullable = false, unique = true)
    private String email;
    @Column(length = 120, nullable = false)
    private String password;
    @Column(length = 13)
    private String phoneNumber;
    @Column(length = 15, nullable = false)
    private String zipCode;
    @Column(nullable = false)
    private Boolean builtIn = false;

}
