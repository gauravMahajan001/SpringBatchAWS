package com.batch.file.adapters.batch.in;

import com.batch.file.dto.BatchDto;
import com.batch.file.ports.batch.in.CustomerBatchPort;
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
public class BatchController {

private final CustomerBatchPort customerBatch;

    @PostMapping("/startBatch")
    public String processFile(@RequestBody BatchDto batchFileDto) throws JobInstanceAlreadyCompleteException,
            JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {

       return customerBatch.start(batchFileDto.getBucketName(), batchFileDto.getFileName());
    }
}
