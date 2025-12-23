// src/main/java/com/example/redisdemo/service/UserService.java
package com.example.redisdemo.service;

import com.example.redisdemo.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    // Simulated database
    private final Map<Long, User> userDatabase = new HashMap<>();

    public UserService() {
        // Initialize with some sample data
        userDatabase.put(1L, new User(1L, "John Doe", "john@example.com"));
        userDatabase.put(2L, new User(2L, "Jane Smith", "jane@example.com"));
        userDatabase.put(3L, new User(3L, "Bob Johnson", "bob@example.com"));
    }

    @Cacheable(value = "users", key = "#id")
    public User findById(Long id) {
        logger.info("Fetching user from database for id: {}", id);

        // Simulate database delay
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        return userDatabase.get(id);
    }

    @CachePut(value = "users", key = "#user.id")
    public User save(User user) {
        logger.info("Saving user to database: {}", user);
        userDatabase.put(user.getId(), user);
        return user;
    }

    @CacheEvict(value = "users", key = "#id")
    public void deleteById(Long id) {
        logger.info("Deleting user from database for id: {}", id);
        userDatabase.remove(id);
    }

    @CacheEvict(value = "users", allEntries = true)
    public void clearCache() {
        logger.info("Clearing all users from cache");
    }

    public Map<Long, User> getAllUsers() {
        logger.info("Fetching all users from database");
        return new HashMap<>(userDatabase);
    }
}