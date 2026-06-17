package com.batch.file.adapters.in.storage;

import com.batch.file.ports.out.storage.S3Port;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/file")
@RequiredArgsConstructor
public class CsvUploadController {

    private final S3Port s3Port;
    /**
     * Generate a presigned URL for uploading a file to S3
     */
    @PostMapping("/upload-url")
    public ResponseEntity<Map<String, String>> generateUploadUrl(@RequestParam String fileName) {
        try {
            log.info("Generating presigned upload URL for fileName: {}", fileName);
            String presignedUrl = s3Port.presignUploadUrl(fileName, Duration.ofMinutes(15));

            Map<String, String> response = new HashMap<>();
            response.put("uploadUrl", presignedUrl);
            response.put("fileName", fileName);

            log.debug("Successfully generated presigned upload URL for fileName: {}", fileName);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error generating presigned upload URL for fileName: {}", fileName, e);
            return ResponseEntity.badRequest().build();
        }
    }
}
