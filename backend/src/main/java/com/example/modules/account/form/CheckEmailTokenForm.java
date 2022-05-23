package com.example.modules.account.form;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class CheckEmailTokenForm {

    @NotBlank
    private String token;

    @Email
    @NotBlank
    private String email;
}
