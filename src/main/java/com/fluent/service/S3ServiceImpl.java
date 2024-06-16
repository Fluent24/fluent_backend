//package com.fluent.service;
//
//import com.fluent.config.S3Properties;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
//import software.amazon.awssdk.core.sync.RequestBody;
//import software.amazon.awssdk.regions.Region;
//import software.amazon.awssdk.services.s3.S3Client;
//import software.amazon.awssdk.services.s3.model.PutObjectRequest;
//
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//
////@Service
//public class S3ServiceImpl {
//
//    private final S3Client s3Client;
//    private final S3Properties s3Properties;
//
//    @Autowired
//    public S3ServiceImpl(S3Properties s3Properties) {
//        this.s3Properties = s3Properties;
//        this.s3Client = S3Client.builder()
//                                .region(Region.of(s3Properties.getRegion()))
//                                .credentialsProvider(DefaultCredentialsProvider.create())
//                                .build();
//    }
//
//    public String uploadAudio(byte[] audioData, String extension) throws IOException {
//        String fileName = generateFileName(extension);
//        Map<String, String> metadata = new HashMap<>();
//        metadata.put("Content-Type", "audio/mpeg");
//        metadata.put("Content-Length", String.valueOf(audioData.length));
//
//        try {
//            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
//                                                                .bucket(s3Properties.getBucketName())
//                                                                .key(fileName)
//                                                                .metadata(metadata)
//                                                                .build();
//            s3Client.putObject(putObjectRequest, RequestBody.fromBytes(audioData));
//            return s3Client.utilities().getUrl(builder -> builder.bucket(s3Properties.getBucketName()).key(fileName)).toExternalForm();
//        } catch (Exception e) {
//            throw new IOException("Failed to upload audio to S3", e);
//        }
//    }
//
//    private String generateFileName(String extension) {
//        return System.currentTimeMillis() + "_" + Math.random() + "." + extension;
//    }
//}