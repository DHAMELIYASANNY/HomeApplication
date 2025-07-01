package com.example.home.controller;

import com.example.home.service.S3Service;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/s3")
@AllArgsConstructor
public class S3Controller {

    private final S3Service s3Service;

    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file) throws Exception {
        String key = s3Service.uploadFile(file);
        return ResponseEntity.ok().body("File uploaded with key: " + key);
    }

    @GetMapping("/file/{key}")
    public ResponseEntity<byte[]> getFile(@PathVariable String key) {
        byte[] data = s3Service.getFile(key);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + key + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(data);
    }
}