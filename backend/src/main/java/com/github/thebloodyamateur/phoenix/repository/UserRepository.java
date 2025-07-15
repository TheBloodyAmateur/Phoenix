package com.github.thebloodyamateur.phoenix.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.github.thebloodyamateur.phoenix.model.auth.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    Optional<User> findById(Long id);
    boolean existsByUsername(String username);
    //boolean existsByRolesID(Long id);
}
