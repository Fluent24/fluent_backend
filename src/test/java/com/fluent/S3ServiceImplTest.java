//package com.fluent;
//
//import com.fluent.config.S3Properties;
//import com.fluent.service.S3ServiceImpl;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import software.amazon.awssdk.core.sync.RequestBody;
//import software.amazon.awssdk.regions.Region;
//import software.amazon.awssdk.services.s3.S3Client;
//import software.amazon.awssdk.services.s3.S3Utilities;
//import software.amazon.awssdk.services.s3.model.GetUrlRequest;
//import software.amazon.awssdk.services.s3.model.PutObjectRequest;
//
//import java.io.IOException;
//import java.net.URI;
//import java.net.URL;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//@SpringBootTest
//public class S3ServiceImplTest {
//
//    @Autowired
//    private S3ServiceImpl s3Service;
//
//    private S3Utilities s3Utilities;
//
//
//    @Test
//    public void testUploadAudio_Success() throws IOException {
//        byte[] audioData = "test audio data".getBytes();
//        String extension = "mp3";
//        String bucketName = "inha-fluent-bucket";
//        String fileName = "1234_test.mp3";
//        String expectedUrl = "https://s3.ap-southeast-1.amazonaws.com/inha-fluent-bucket/" + fileName;
//
//        String fileUrl = s3Service.uploadAudio(audioData, extension);
//        System.out.println(fileUrl);
//
//    }
//
//    //@Test
////    public void testUploadAudio_Failure() throws IOException {
////        byte[] audioData = "test audio data".getBytes();
////        String extension = "mp3";
////        String bucketName = "inha-fluent-bucket";
////
////        doThrow(new RuntimeException("S3 upload failed")).when(s3Client).putObject(any(PutObjectRequest.class), any(RequestBody.class));
////
////        IOException exception = assertThrows(IOException.class, () -> {
////            s3Service.uploadAudio(audioData, extension);
////        });
////
////        assertEquals("Failed to upload audio to S3", exception.getMessage());
////    }
//}