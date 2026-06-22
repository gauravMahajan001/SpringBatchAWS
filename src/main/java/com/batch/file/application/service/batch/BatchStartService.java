package com.batch.file.application.service.batch;

import com.batch.file.constant.ApplicationConstant;
import com.batch.file.ports.in.batch.BatchStartPort;
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
import org.springframework.stereotype.Service;

import java.nio.file.Path;

@Slf4j
@Service
@RequiredArgsConstructor
public class BatchStartService implements BatchStartPort {
   //CustomerJob is the name of the job defined in the JobConfig class
    private final Job batchJob;
    private final JobLauncher jobLauncher;
    private final  S3DownloadService s3DownloadService;

    @Override
    public String start(String bucketName, String fileName) throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {

        Path localFile = s3DownloadService.downloadToTempFile(bucketName, fileName);

        JobParameters parameters = new JobParametersBuilder()
                .addString(ApplicationConstant.BUCKET_NAME, fileName)
                .addString(ApplicationConstant.FILE_NAME, fileName)
                .addString(ApplicationConstant.LOCAL_FILE, localFile.toString())
                .toJobParameters();
        log.info("batch started");
        jobLauncher.run(batchJob, parameters);
        return ApplicationConstant.JOB_START;
    }
}
