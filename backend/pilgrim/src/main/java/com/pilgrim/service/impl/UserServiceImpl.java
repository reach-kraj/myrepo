package com.pilgrim.service.impl;

import com.pilgrim.mapper.UserRegistrationRequest;
import com.pilgrim.entity.Address;
import com.pilgrim.entity.Role;
import com.pilgrim.entity.User;
import com.pilgrim.repository.AddressRepository;
import com.pilgrim.repository.RoleRepository;
import com.pilgrim.repository.UserRepository;
import com.pilgrim.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AddressRepository addressRepository;

    @Override
    @Transactional
    public User registerAdminUser(UserRegistrationRequest request) {
        // Admin users get Admin role (role_id = 1)
        Role adminRole = roleRepository.findByRoleIdAndIsActiveTrue(1)
                .orElseThrow(() -> new RuntimeException("Admin role not found"));

        return createUser(request, adminRole.getRoleId());
    }

    @Override
    @Transactional
    public User registerEndUser(UserRegistrationRequest request) {
        // End users get User role (role_id = 2)
        Role userRole = roleRepository.findByRoleIdAndIsActiveTrue(2)
                .orElseThrow(() -> new RuntimeException("User role not found"));

        return createUser(request, userRole.getRoleId());
    }

    private User createUser(UserRegistrationRequest request, Integer roleId) {
        // Check if user already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        // Create user
        User user = new User();
        user.setUserId(request.getUserId());
        user.setFirstname(request.getFirstname());
        user.setMiddlename(request.getMiddlename());
        user.setLastname(request.getLastname());
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setAddress(request.getAddress());
        user.setRoleId(roleId);
        user.setCreatedBy(request.getUserId());
        user.setIsActive(true);

        User savedUser = userRepository.save(user);

        // Create address if provided
        if (request.getStreet() != null || request.getCity() != null) {
            Address address = new Address();
            address.setUserId(savedUser.getUserId());
            address.setStreet(request.getStreet());
            address.setCity(request.getCity());
            address.setState(request.getState());
            address.setCountry(request.getCountry());
            address.setPostalCode(request.getPostalCode());
            address.setCreatedBy(request.getUserId());
            address.setIsActive(true);

            addressRepository.save(address);
        }

        return savedUser;
    }

    @Override
    public User getUserById(String userId) {
        return userRepository.findByUserIdAndIsActiveTrue(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public boolean isEmailExists(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public boolean isUsernameExists(String username) {
        return userRepository.existsByUsername(username);
    }

    // =============== NEW METHODS - SIMPLE VERSION ===============

    @Override
    public Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findByIsActiveTrue(pageable);
    }

    @Override
    public List<User> getAllUsersSimple() {
        return userRepository.findByIsActiveTrueOrderByCreatedAtDesc();
    }

    @Override
    public Page<User> searchUsers(String email, String username, String firstname, Integer roleId, Pageable pageable) {
        // Simple implementation - search by one parameter at a time
        if (email != null && !email.trim().isEmpty()) {
            return userRepository.findByEmailContainingIgnoreCaseAndIsActiveTrue(email, pageable);
        }
        if (username != null && !username.trim().isEmpty()) {
            return userRepository.findByUsernameContainingIgnoreCaseAndIsActiveTrue(username, pageable);
        }
        if (firstname != null && !firstname.trim().isEmpty()) {
            return userRepository.findByFirstnameContainingIgnoreCaseAndIsActiveTrue(firstname, pageable);
        }
        if (roleId != null) {
            return userRepository.findByRoleIdAndIsActiveTrue(roleId, pageable);
        }

        // Default: return all active users
        return userRepository.findByIsActiveTrue(pageable);
    }
}