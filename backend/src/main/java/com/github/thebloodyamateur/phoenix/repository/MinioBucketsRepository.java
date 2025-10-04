package com.github.thebloodyamateur.phoenix.repository;

import com.github.thebloodyamateur.phoenix.model.MinioBucket;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MinioBucketsRepository extends JpaRepository<MinioBucket, Long> {
    void deleteByUserId(Long userId);
}