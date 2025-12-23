package com.pilgrim.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class HealthController {

    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> health() {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Server is healthy");
        response.put("timestamp", LocalDateTime.now());
        response.put("status", "UP");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/users/check-server")
    public ResponseEntity<Map<String, Object>> checkServer() {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Backend services are operational");
        response.put("timestamp", LocalDateTime.now());
        response.put("status", "UP");
        return ResponseEntity.ok(response);
    }
    @GetMapping("/check-server")
    public ResponseEntity<Map<String, Object>> checkServerHealth() {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Server is healthy");
        response.put("timestamp", LocalDateTime.now());
        response.put("status", "UP");
        return ResponseEntity.ok(response);
    }
}