// UserRepository.java
package com.pilgrim.repository;

import com.pilgrim.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUserIdAndIsActiveTrue(String userId);
    Optional<User> findByEmailAndIsActiveTrue(String email);
    Optional<User> findByUsernameAndIsActiveTrue(String username);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);

    // Pagination methods
    Page<User> findByIsActiveTrue(Pageable pageable);
    List<User> findByIsActiveTrueOrderByCreatedAtDesc();

    // Role-based queries
    List<User> findByRoleIdAndIsActiveTrue(Integer roleId);
    Page<User> findByRoleIdAndIsActiveTrue(Integer roleId, Pageable pageable);

    // Search methods (simple version)
    Page<User> findByEmailContainingIgnoreCaseAndIsActiveTrue(String email, Pageable pageable);
    Page<User> findByUsernameContainingIgnoreCaseAndIsActiveTrue(String username, Pageable pageable);
    Page<User> findByFirstnameContainingIgnoreCaseAndIsActiveTrue(String firstname, Pageable pageable);
}