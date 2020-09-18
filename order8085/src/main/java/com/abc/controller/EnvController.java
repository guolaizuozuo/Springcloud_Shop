package com.abc.controller;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/env")
@RefreshScope
public class EnvController {

/*    @Value("${env}")
    private String env;

    @GetMapping("/print")
    public String print(){
        return env;
    }*/
}
