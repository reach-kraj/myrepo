package com.thymeleaf.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ThymeleafController {
    @GetMapping("/")
    public String getMessage() {
        return "This is a sample message that shows in Angular";
    }
}
