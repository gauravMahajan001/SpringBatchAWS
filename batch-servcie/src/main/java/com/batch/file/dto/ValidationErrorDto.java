package com.batch.file.dto;

import lombok.Data;

@Data
public class ValidationErrorDto {
    private final String errorField;
    private final String errorMessage;
}
