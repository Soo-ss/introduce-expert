package com.api.backend.modules.exception;

import com.api.backend.infra.advice.exception.CAuthenticationEntryPointException;
import com.api.backend.infra.response.model.CommonResult;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exception")
public class ExceptionController {

    @GetMapping("/entrypoint")
    public CommonResult entryPointException(){
        throw new CAuthenticationEntryPointException();
    }

    @GetMapping("/access-denied")
    public CommonResult accessDeniedException(){
        throw new AccessDeniedException("");
    }
}
