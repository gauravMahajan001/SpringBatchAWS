package com.batch.file.adapters.in.batch;

import com.batch.file.application.service.batch.CsvAuditService;
import com.batch.file.dto.batch.CsvFileDto;
import com.batch.file.ports.in.batch.BatchStartPort;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CsvFileController {

    private final BatchStartPort startPort;
    private final CsvAuditService csvAuditService;

    @PostMapping("/startBatch")
    public String processFile(@Valid @RequestBody CsvFileDto csvFileDto) throws JobInstanceAlreadyCompleteException,
            JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
//AuditRecord existing =
//        auditRepository.findByFileName(fileName);
        csvAuditService.audit(csvFileDto.getBucketName(), csvFileDto.getFileName());
        return startPort.start(csvFileDto.getBucketName(), csvFileDto.getFileName());
    }
}
