package com.github.thebloodyamateur.phoenix.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.github.thebloodyamateur.phoenix.dto.GeneralResponse;
import com.github.thebloodyamateur.phoenix.model.MinioBucket;
import com.github.thebloodyamateur.phoenix.model.MinioObject;
import com.github.thebloodyamateur.phoenix.repository.MinioBucketsRepository;
import com.github.thebloodyamateur.phoenix.repository.MinioObjectsRepository;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.RemoveBucketArgs;
import jakarta.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j(topic = "FileService")
public class FileService {
    @Autowired
    private MinioClient minioClient;

    @Autowired
    private MinioBucketsRepository minioBucketsRepository;

    @Autowired
    private MinioObjectsRepository minioObjectsRepository;

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

    public ResponseEntity<GeneralResponse> uploadFile(MultipartFile fileData, String fileName, String bucketName) {
        try {

            MinioBucket bucket = minioBucketsRepository.findByBucketName(bucketName).orElse(null);

            if (bucket == null) {
                log.error("Bucket '{}' not found in database.", bucketName);
                return ResponseEntity.status(500).body(new GeneralResponse("Bucket not found."));
            }

            minioClient.putObject(
                io.minio.PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(fileName)
                    .stream(fileData.getInputStream(), fileData.getSize(), -1)
                    .build()
            );
            log.info("File '{}' uploaded successfully to bucket '{}'.", fileName, bucketName);

            MinioObject minioObject = MinioObject.builder()
                .name(fileName)
                .minioPath(bucketName + "/" + fileName)
                .size(fileData.getSize())
                .type(MinioObject.ObjectType.FILE)
                .build();
            
            minioObject.setMinioBucket(bucket);
            minioObjectsRepository.save(minioObject);

            return ResponseEntity.ok(new GeneralResponse("File uploaded successfully."));
        } catch (Exception e) {
            log.error("Error uploading file to bucket '{}': {}", bucketName, e.getMessage());
            return ResponseEntity.status(500).body(new GeneralResponse("Failed to upload file."));
        }
    }

    public ResponseEntity<GeneralResponse> deleteFile(String bucketName, String fileName) {
        MinioBucket bucket = minioBucketsRepository.findByBucketName(bucketName).orElse(null);
        if (bucket == null) {
            log.error("Bucket '{}' not found in database.", bucketName);
            return ResponseEntity.status(500).body(new GeneralResponse("Bucket not found."));
        }

        MinioObject minioObject = minioObjectsRepository.findByMinioBucketAndName(bucket, fileName).orElse(null);
        if (minioObject == null) {
            log.error("File '{}' not found in bucket '{}'.", fileName, bucketName);
            return ResponseEntity.status(500).body(new GeneralResponse("File not found in the specified bucket."));
        }

        try {
            minioClient.removeObject(
                io.minio.RemoveObjectArgs.builder()
                    .bucket(bucketName)
                    .object(fileName)
                    .build()
            );
            log.info("File '{}' deleted successfully from bucket '{}'.", fileName, bucketName);

            minioObjectsRepository.delete(minioObject);

            return ResponseEntity.ok(new GeneralResponse("File deleted successfully."));
        } catch (Exception e) {
            log.error("Error deleting file '{}' from bucket '{}': {}", fileName, bucketName, e.getMessage());
            return ResponseEntity.status(500).body(new GeneralResponse("Failed to delete file."));
        }
    }
}
