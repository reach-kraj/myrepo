package com.example.springsecurityeg.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/public")
    public String publicApi() {
        return "This is a public endpoint.";
    }

    @GetMapping("/private")
    public String privateApi() {
        return "This is a secured endpoint. You are authenticated.";
    }
}
