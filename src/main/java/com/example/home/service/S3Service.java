package com.example.home.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.core.ResponseInputStream;

import java.io.IOException;

@Service
public class S3Service {

    private final S3Client s3Client;

    @Value("${AWS_S3_BUCKET}")
    private String bucket;

    public S3Service(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    public String uploadFile(MultipartFile file) throws IOException {
        String key = file.getOriginalFilename();
        s3Client.putObject(PutObjectRequest.builder()
                        .bucket(bucket)
                        .key(key)
                        .contentType(file.getContentType())
                        .build(),
                software.amazon.awssdk.core.sync.RequestBody.fromBytes(file.getBytes()));
        return key;
    }

    public byte[] getFile(String key) {
        try (ResponseInputStream<GetObjectResponse> response = s3Client.getObject(GetObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .build())) {
            return response.readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException("Failed to read file from S3", e);
        }
    }
}