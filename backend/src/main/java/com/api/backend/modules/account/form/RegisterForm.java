package com.api.backend.modules.account.form;

import lombok.Data;

@Data
public class RegisterForm {

    private String email;

    private String password;

    private String name;
}
