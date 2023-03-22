package com.hoopoe.dto;

import com.hoopoe.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private  Boolean builtIn;

    private Set<String> roles;

    public void setRoles(Set<Role> roles){
        Set<String> roleStr = new HashSet<>();

        roles.forEach(r->{
            roleStr.add(r.getType().getName());
        });
        this.roles = roleStr;

    }
}
