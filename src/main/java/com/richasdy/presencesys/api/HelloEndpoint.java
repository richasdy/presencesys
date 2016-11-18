package com.richasdy.presencesys.api;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class HelloEndpoint {
    
    @RequestMapping("/hello")
    public String index() {
        return "Greetings from Spring Boot!";
    }
    
}
