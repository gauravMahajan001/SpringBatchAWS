package com.batch.file.adapters.in.storage;

import com.batch.file.domain.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/file")
@RequiredArgsConstructor
public class FileUploadController {

    private final S3Service s3Service;

    /**
     * Generate a presigned URL for uploading a file to S3
     */
    @PostMapping("/upload-url")
    public ResponseEntity<Map<String, String>> generateUploadUrl(@RequestParam String fileName) {
        try {
            String presignedUrl = s3Service.presignUploadUrl(fileName, Duration.ofMinutes(15));

            Map<String, String> response = new HashMap<>();
            response.put("uploadUrl", presignedUrl);
            response.put("fileName", fileName);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
