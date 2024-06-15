package com.fluent.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

//@Service
public class S3Service {

    private final S3Client s3Client;
    private final String bucketName;

    public S3Service(@Value("${aws.s3.bucket-name}") String bucketName) {
        this.bucketName = bucketName;
        this.s3Client = S3Client.builder()
                                .region(Region.AP_SOUTHEAST_1)
                                .credentialsProvider(ProfileCredentialsProvider.create())
                                .build();
    }

    public String uploadFile(MultipartFile file) throws IOException {
        String fileName = generateFileName(file);
        Path tempFile = Files.createTempFile("temp-", fileName);
        file.transferTo(tempFile.toFile());

        try {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                                                                .bucket(bucketName)
                                                                .key(fileName)
                                                                .build();

            s3Client.putObject(putObjectRequest, tempFile);
            return s3Client.utilities().getUrl(builder -> builder.bucket(bucketName).key(fileName)).toExternalForm();
        } catch (S3Exception e) {
            throw new IOException("Failed to upload file to S3", e);
        } finally {
            Files.deleteIfExists(tempFile);
        }
    }

    private String generateFileName(MultipartFile file) {
        return System.currentTimeMillis() + "_" + file.getOriginalFilename().replaceAll(" ", "_");
    }
}