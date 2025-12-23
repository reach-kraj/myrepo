package com.example.grpceg.controller;

import com.example.grpceg.client.GreeterClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    private final GreeterClient client;

    public HelloController(GreeterClient client) {
        this.client = client;
    }

    @GetMapping("/hello")
    public String hello(@RequestParam String name) {
        return client.sayHello(name);
    }
}
