package com.api.backend.modules.account.form;

import lombok.Data;

@Data
public class LoginForm {

    private String email;

    private String password;
}
