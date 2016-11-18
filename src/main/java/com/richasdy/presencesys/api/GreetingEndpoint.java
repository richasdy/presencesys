package com.richasdy.presencesys.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.richasdy.presencesys.service.GreetingService;


@Controller
public class GreetingEndpoint {

    private final GreetingService service;

    public GreetingEndpoint(GreetingService service) {
        this.service = service;
    }

    @RequestMapping("/greeting")
    public @ResponseBody String greeting() {
        return service.greet();
    }

}
