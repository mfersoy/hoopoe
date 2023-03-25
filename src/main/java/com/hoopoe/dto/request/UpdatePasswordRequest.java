package com.hoopoe.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePasswordRequest {

    @NotBlank(message = "Please provide ol password")
    private String oldPassword;

    @NotBlank(message = "Please provide new password")
    @Size(min = 4, max = 20, message = "Please provide correct size for password")
    private String newPassword;

}
