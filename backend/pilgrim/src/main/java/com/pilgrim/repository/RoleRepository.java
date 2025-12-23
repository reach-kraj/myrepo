package com.pilgrim.repository;

// RoleRepository.java

import com.pilgrim.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByRoleName(String roleName);
    Optional<Role> findByRoleIdAndIsActiveTrue(Integer roleId);
}

