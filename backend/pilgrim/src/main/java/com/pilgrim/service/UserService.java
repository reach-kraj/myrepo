package com.pilgrim.service;

import com.pilgrim.mapper.UserRegistrationRequest;
import com.pilgrim.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    User registerAdminUser(UserRegistrationRequest request);
    User registerEndUser(UserRegistrationRequest request);
    User getUserById(String userId);
    boolean isEmailExists(String email);
    boolean isUsernameExists(String username);

    // New methods
    Page<User> getAllUsers(Pageable pageable);
    List<User> getAllUsersSimple();
    Page<User> searchUsers(String email, String username, String firstname, Integer roleId, Pageable pageable);
}