package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/rest/home")
public class HomeController {
    @ResponseBody
    @GetMapping("")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("Congratulations! You are authorized.");
    }
}