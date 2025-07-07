package com.github.thebloodyamateur.phoenix.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.github.thebloodyamateur.phoenix.model.auth.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRole(String roleName);
    boolean existsByRole(String roleName);
}
