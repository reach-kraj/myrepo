package com.example.mockitoeg.service;

import org.springframework.stereotype.Service;

@Service
public class UserService {

    public String getUserName(Long userId) {
        // Simulating some logic, no database involved
        return "User-" + userId;
    }

    public String createUser(String name) {
        // Simulating user creation
        return "Created: " + name;
    }
}
