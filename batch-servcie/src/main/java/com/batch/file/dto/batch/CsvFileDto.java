package com.batch.file.dto.batch;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CsvFileDto {
    @NotBlank(message = "bucketName is required")
    private String bucketName;
    @NotBlank(message = "fileName is required")
    private String fileName;
}
