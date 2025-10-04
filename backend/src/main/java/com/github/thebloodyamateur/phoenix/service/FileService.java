package com.github.thebloodyamateur.phoenix.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.thebloodyamateur.phoenix.model.MinioBucket;
import com.github.thebloodyamateur.phoenix.repository.MinioBucketsRepository;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.RemoveBucketArgs;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j(topic = "FileService")
public class FileService {
    @Autowired
    private MinioClient minioClient;

    @Autowired
    private MinioBucketsRepository minioBucketsRepository;

    public boolean createBucket(String bucketName) {
        try {
            boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            log.info("Bucket '{}' exists: {}", bucketName, found);
            if (!found) {
                log.info("Creating bucket '{}'", bucketName);
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }
            return true;
        } catch (Exception e) {
            log.error("Error occurred while creating bucket '{}': {}", bucketName, e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteBucket(String bucketName) {
        log.info("Attempting to delete bucket '{}'", bucketName);
        try {
            boolean exists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!exists) {
                log.info("Bucket '{}' does not exist.", bucketName);
                return false;
            }
    
            minioClient.removeBucket(RemoveBucketArgs.builder().bucket(bucketName).build());
            log.info("Bucket '{}' deleted successfully.", bucketName);
            return true;
        } catch (Exception e) {
            log.error("Error deleting bucket '{}': {}", bucketName, e.getMessage());
            return false;
        }
    }

    public boolean deleteBucketById(Long id) {
        MinioBucket bucket = minioBucketsRepository.findById(id).orElse(null);
        log.info("Attempting to delete bucket for ID: {}", id);
        if(bucket == null) {
            log.warn("No bucket found for user ID: {}", id);
            return false;
        }

        RemoveBucketArgs args = RemoveBucketArgs.builder().bucket(bucket.getName()).build();

        try {
            minioClient.removeBucket(args);
            log.info("Bucket '{}' deleted successfully.", bucket.getName());
            return true;
        } catch (Exception e) {
            log.error("Error occurred while deleting bucket '{}': {}", bucket.getName(), e.getMessage());
            e.printStackTrace();
            return false;
        }
        
    }
}
