package com.batch.file.adapters.in.storage;

import com.batch.file.dto.storage.CsvUploadRequestDto;
import com.batch.file.dto.storage.CsvUploadResponseDto;
import com.batch.file.ports.out.storage.S3Port;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<CsvUploadResponseDto> generateUploadUrl(@RequestBody CsvUploadRequestDto requestDto) {
        try {
            log.info("Generating presigned upload URL for fileName: {}", requestDto.getFileName());
            String presignedUrl = s3Port.presignUploadUrl(requestDto.getFileName(), Duration.ofMinutes(15));
            CsvUploadResponseDto responseDto = new CsvUploadResponseDto();
            responseDto.setFileName(requestDto.getFileName());
            responseDto.setUploadUrl(presignedUrl);

            log.debug("Successfully generated presigned upload URL for fileName: {}", requestDto.getFileName());
            return ResponseEntity.ok(responseDto);
        } catch (Exception e) {
            log.error("Error generating presigned upload URL for fileName: {}", requestDto.getFileName(), e);
            return ResponseEntity.badRequest().build();
        }
    }
}
