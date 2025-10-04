package com.github.thebloodyamateur.phoenix.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.minio.MinioClient;

@Service
public class FileService {
    @Autowired
    private MinioClient minioClient;
}
