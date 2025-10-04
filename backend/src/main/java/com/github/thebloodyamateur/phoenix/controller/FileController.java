package com.github.thebloodyamateur.phoenix.controller;

import org.springframework.web.bind.annotation.RestController;

import com.github.thebloodyamateur.phoenix.dto.GeneralResponse;
import com.github.thebloodyamateur.phoenix.service.FileService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/files")
@Slf4j(topic = "FileController")
public class FileController {

    @Autowired
    private FileService fileService;

    @DeleteMapping("/bucket/name/{bucketname}")
    public ResponseEntity<GeneralResponse> deleteBucketByName(@PathVariable String bucketname) {
        boolean isDeleted = fileService.deleteBucket(bucketname);
        if (isDeleted) {
            log.info("Bucket '{}' deleted successfully.", bucketname);
            return ResponseEntity.ok(new GeneralResponse("Bucket deleted successfully."));
        } else {
            log.error("Failed to delete bucket '{}'.", bucketname);
            return ResponseEntity.status(500).body(new GeneralResponse("Failed to delete bucket."));
        }
    }

    @DeleteMapping("/bucket/id/{id}")
    public ResponseEntity<GeneralResponse> deleteBucketById(@PathVariable Long id) {
        boolean isDeleted = fileService.deleteBucketById(id);
        if (isDeleted) {
            log.info("Bucket with ID '{}' deleted successfully.", id);
            return ResponseEntity.ok(new GeneralResponse("Bucket deleted successfully."));
        } else {
            log.error("Failed to delete bucket with ID '{}'.", id);
            return ResponseEntity.status(500).body(new GeneralResponse("Failed to delete bucket."));
        }
    }
}
