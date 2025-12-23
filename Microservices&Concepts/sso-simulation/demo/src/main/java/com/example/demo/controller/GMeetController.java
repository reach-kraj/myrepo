package com.example.demo.controller;


import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/gmeet")
public class GMeetController {
    @GetMapping
    public String getGMeetData(@AuthenticationPrincipal Jwt jwt) {
        return "Welcome to GMeet API, " + jwt.getClaimAsString("preferred_username") + "!";
    }
}