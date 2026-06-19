package com.batch.file.exception;

import com.batch.file.dto.ValidationErrorDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

   @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ValidationErrorDto>> handleValidationException(MethodArgumentNotValidException ex) {

        List<ValidationErrorDto> errorDtos = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> new ValidationErrorDto(fieldError.getField(), fieldError.getDefaultMessage()))
                .toList();

        return ResponseEntity.badRequest().body(errorDtos);
    }
}
