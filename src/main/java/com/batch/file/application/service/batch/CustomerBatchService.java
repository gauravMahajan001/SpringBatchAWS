package com.batch.file.application.service.batch;

import com.batch.file.constant.ApplicationConstant;
import com.batch.file.ports.in.batch.CustomerBatchPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomerBatchService implements CustomerBatchPort {

    private final Job customerJob;
    private final JobLauncher jobLauncher;

    @Override
    public String start(String bucketName, String fileName) throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {

        JobParameters parameters = new JobParametersBuilder()
                .addLong(ApplicationConstant.RUN_ID, System.currentTimeMillis())
                .addString(ApplicationConstant.FILE_NAME, fileName)
                .addString(ApplicationConstant.PROCESS_DATE,  LocalDate.now().toString())
                .toJobParameters();
        log.info("batch started");
        jobLauncher.run(customerJob, parameters);
        return ApplicationConstant.JOB_START;
    }
}
