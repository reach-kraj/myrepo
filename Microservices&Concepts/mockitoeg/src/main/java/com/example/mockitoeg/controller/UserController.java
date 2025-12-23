package com.example.mockitoeg.controller;

import com.example.mockitoeg.service.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public String getUser(@PathVariable Long id) {
        return userService.getUserName(id);
    }

    @PostMapping
    public String createUser(@RequestBody String name) {
        return userService.createUser(name);
    }
}