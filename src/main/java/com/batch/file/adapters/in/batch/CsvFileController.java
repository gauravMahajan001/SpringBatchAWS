package com.batch.file.adapters.in.batch;

import com.batch.file.dto.batch.CsvRecordDto;
import com.batch.file.ports.in.batch.StartPort;
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

    private final StartPort startPort;

    @PostMapping("/startBatch")
    public String processFile(@RequestBody CsvRecordDto batchFileDto) throws JobInstanceAlreadyCompleteException,
            JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {

        return startPort.start(batchFileDto.getBucketName(), batchFileDto.getFileName());
    }
}
