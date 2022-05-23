package com.example.modules.account.form;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class LoginForm {

    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Length(min = 3, max = 50)
    private String password;
}
