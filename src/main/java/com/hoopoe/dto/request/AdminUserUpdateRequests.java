package com.hoopoe.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdminUserUpdateRequests {

    @Size(max = 50)
    @NotBlank(message = "Please provide your First name")
    private String firstName;

    @Size(max = 50)
    @NotBlank(message = "Please provide your last name")
    private String lastName;

    @Email(message = "Please provide valid email")
    private String email;

    @Size(min = 4, max = 20, message = "Please provide correct size for password")
    private String password;

    private String phoneNumber;

    private Boolean builtIn;

    private Set<String> roles;

}
