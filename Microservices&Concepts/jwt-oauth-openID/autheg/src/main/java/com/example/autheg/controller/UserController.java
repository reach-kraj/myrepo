package com.example.autheg.controller;


import org.springframework.security.core.annotation.AuthenticationPrincipal; // Injects the currently authenticated principal
import org.springframework.security.oauth2.core.oidc.user.OidcUser; // Represents the authenticated user via OIDC
import org.springframework.web.bind.annotation.GetMapping; // Maps HTTP GET requests
import org.springframework.web.bind.annotation.RestController; // Marks this as a REST controller

import java.util.Map;

@RestController // Defines a REST controller
public class UserController {

    @GetMapping("/") // Maps GET request to "/" endpoint
    public String home() {
        return "Welcome to the public endpoint."; // Public endpoint accessible by all
    }

    @GetMapping("/secure") // Maps GET request to "/secure" endpoint
    public String secure(@AuthenticationPrincipal OidcUser oidcUser) { // Injects OIDC authenticated user
        return "Hello, " + oidcUser.getFullName() + ", your email is " + oidcUser.getEmail(); // Personalized secured message
    }
    
    @GetMapping("/claims") // Debug endpoint to inspect OIDC claims
    public Map<String, Object> claims(@AuthenticationPrincipal OidcUser oidcUser) {
        return oidcUser.getClaims(); // Returns all claims in the ID token
    }
}
