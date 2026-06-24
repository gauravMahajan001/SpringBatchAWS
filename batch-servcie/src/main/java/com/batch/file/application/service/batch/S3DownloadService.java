package com.batch.file.application.service.batch;

import com.batch.file.constant.ApplicationConstant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3DownloadService {
    private final S3Client s3Client;

    public Path downloadToTempFile(String bucketName, String fileName) {
        try {
            Path tempDir = Path.of(ApplicationConstant.TEMP_PATH);
            Files.createDirectories(tempDir);
            Path tempFile = tempDir.resolve(fileName);
            if (!Files.exists(tempFile)) {

                log.info("Downloaded file from S3. bucket={}, key={}, localFile={}",
                        bucketName, fileName, tempFile);
                GetObjectRequest request = GetObjectRequest.builder().bucket(bucketName).key(fileName).build();

                s3Client.getObject(request, tempFile);
            } else {

                log.info("Using existing local file {}", tempFile);
            }
            return tempFile;
        } catch (IOException e) {
            throw new RuntimeException(String.format("Failed to download file from S3. bucket=%s, key=%s", bucketName, fileName), e);
        }
    }
}
