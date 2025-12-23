package com.pilgrim.controller;

import com.pilgrim.mapper.UserRegistrationRequest;
import com.pilgrim.entity.User;
import com.pilgrim.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;

    // =============== REGISTRATION ENDPOINTS ===============

    @PostMapping("/admin/register")
    public ResponseEntity<Map<String, Object>> registerAdminUser(@Valid @RequestBody UserRegistrationRequest request) {
        try {
            User user = userService.registerAdminUser(request);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Admin user registered successfully");
            response.put("data", buildUserResponse(user));
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Failed to register admin user: " + e.getMessage());
            response.put("data", null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PostMapping("/mobile/register")
    public ResponseEntity<Map<String, Object>> registerEndUser(@Valid @RequestBody UserRegistrationRequest request) {
        try {
            User user = userService.registerEndUser(request);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "User registered successfully");
            response.put("data", buildUserResponse(user));
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Failed to register user: " + e.getMessage());
            response.put("data", null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    // =============== GET USER ENDPOINTS ===============

    @GetMapping("/admin/{userId}")
    public ResponseEntity<Map<String, Object>> getAdminUserById(@PathVariable String userId) {
        try {
            User user = userService.getUserById(userId);

            // Admin can see more details
            Map<String, Object> userData = buildDetailedUserResponse(user);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Admin user retrieved successfully");
            response.put("data", userData);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "User not found: " + e.getMessage());
            response.put("data", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping("/mobile/{userId}")
    public ResponseEntity<Map<String, Object>> getMobileUserById(@PathVariable String userId) {
        try {
            User user = userService.getUserById(userId);

            // Mobile users get limited profile data
            Map<String, Object> userData = buildUserResponse(user);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "User profile retrieved successfully");
            response.put("data", userData);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "User not found: " + e.getMessage());
            response.put("data", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    // Generic endpoint (backward compatibility)
    @GetMapping("/{userId}")
    public ResponseEntity<Map<String, Object>> getUserById(@PathVariable String userId) {
        try {
            User user = userService.getUserById(userId);
            Map<String, Object> userData = buildUserResponse(user);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "User retrieved successfully");
            response.put("data", userData);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "User not found: " + e.getMessage());
            response.put("data", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    // =============== GET ALL USERS ENDPOINTS ===============

    @GetMapping("/admin/all")
    public ResponseEntity<Map<String, Object>> getAllUsersForAdmin(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {
        try {
            Sort sort = sortDir.equalsIgnoreCase("desc") ?
                    Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();

            Pageable pageable = PageRequest.of(page, size, sort);
            Page<User> userPage = userService.getAllUsers(pageable);

            List<Map<String, Object>> users = userPage.getContent().stream()
                    .map(this::buildDetailedUserResponse)
                    .collect(Collectors.toList());

            Map<String, Object> pageData = new HashMap<>();
            pageData.put("users", users);
            pageData.put("currentPage", userPage.getNumber());
            pageData.put("totalPages", userPage.getTotalPages());
            pageData.put("totalElements", userPage.getTotalElements());
            pageData.put("size", userPage.getSize());

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Users retrieved successfully");
            response.put("data", pageData);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Failed to retrieve users: " + e.getMessage());
            response.put("data", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/admin/all/simple")
    public ResponseEntity<Map<String, Object>> getAllUsersSimple() {
        try {
            List<User> users = userService.getAllUsersSimple();

            List<Map<String, Object>> usersList = users.stream()
                    .map(this::buildUserResponse)
                    .collect(Collectors.toList());

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "All users retrieved successfully");
            response.put("data", usersList);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Failed to retrieve users: " + e.getMessage());
            response.put("data", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // =============== SEARCH ENDPOINTS ===============

    @GetMapping("/admin/search")
    public ResponseEntity<Map<String, Object>> searchUsers(
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String firstname,
            @RequestParam(required = false) Integer roleId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<User> userPage = userService.searchUsers(email, username, firstname, roleId, pageable);

            List<Map<String, Object>> users = userPage.getContent().stream()
                    .map(this::buildDetailedUserResponse)
                    .collect(Collectors.toList());

            Map<String, Object> pageData = new HashMap<>();
            pageData.put("users", users);
            pageData.put("currentPage", userPage.getNumber());
            pageData.put("totalPages", userPage.getTotalPages());
            pageData.put("totalElements", userPage.getTotalElements());

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Search completed successfully");
            response.put("data", pageData);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Search failed: " + e.getMessage());
            response.put("data", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // =============== VALIDATION ENDPOINTS ===============

    @GetMapping("/check-email/{email}")
    public ResponseEntity<Map<String, Object>> checkEmailExists(@PathVariable String email) {
        boolean exists = userService.isEmailExists(email);
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Email check completed");
        response.put("data", exists);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/check-username/{username}")
    public ResponseEntity<Map<String, Object>> checkUsernameExists(@PathVariable String username) {
        boolean exists = userService.isUsernameExists(username);
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Username check completed");
        response.put("data", exists);
        return ResponseEntity.ok(response);
    }

    // =============== HELPER METHODS ===============

    private Map<String, Object> buildUserResponse(User user) {
        Map<String, Object> userData = new HashMap<>();
        userData.put("userId", user.getUserId());
        userData.put("firstname", user.getFirstname());
        userData.put("middlename", user.getMiddlename());
        userData.put("lastname", user.getLastname());
        userData.put("username", user.getUsername());
        userData.put("email", user.getEmail());
        userData.put("phone", user.getPhone());
        userData.put("roleId", user.getRoleId());
        userData.put("isActive", user.getIsActive());
        return userData;
    }

    private Map<String, Object> buildDetailedUserResponse(User user) {
        Map<String, Object> userData = buildUserResponse(user);
        // Add additional fields for admin view
        userData.put("address", user.getAddress());
        userData.put("createdAt", user.getCreatedAt());
        userData.put("updatedAt", user.getUpdatedAt());
        userData.put("createdBy", user.getCreatedBy());
        userData.put("updatedBy", user.getUpdatedBy());
        return userData;
    }
}
