package com.hoopoe.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @Column(length = 15)
    private String phoneNumber;

    @Column(nullable = false)
    private Boolean builtIn = false;

    @ManyToMany
    @JoinTable(name = "t_user_role", joinColumns = @JoinColumn(name ="user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

}
