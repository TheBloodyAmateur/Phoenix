package com.github.thebloodyamateur.phoenix.repository;

import com.github.thebloodyamateur.phoenix.model.MinioBucket;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface MinioBucketsRepository extends JpaRepository<MinioBucket, Long> {
    void deleteByUserId(Long userId);
    @Query("SELECT b FROM MinioBucket b WHERE b.name = :bucketName")
    Optional<MinioBucket> findByBucketName(String bucketName);
}