package com.api.backend.modules.hello;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"0. TEST"})
@RestController
@RequestMapping("/hello")
public class HelloController {

    @GetMapping("/test")
    public String hello(){
        return "hello ec2";
    }
}
