package com.batch.file.dto.storage;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CsvUploadRequestDto {
    @NotBlank(message = "fileName is required")
    private String fileName;
}
