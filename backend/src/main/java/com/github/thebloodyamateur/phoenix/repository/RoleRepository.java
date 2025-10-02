package com.github.thebloodyamateur.phoenix.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.thebloodyamateur.phoenix.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Boolean> existsByName(String roleName);
    Role findByName(String roleName);
}
