package com.batch.file.application.service.batch;

import com.batch.file.constant.ApplicationConstant;
import com.batch.file.entity.batch.AuditRecord;
import com.batch.file.ports.in.batch.BatchStartPort;
import com.batch.file.ports.out.batch.PersistenceAuditRecordPort;
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
    private final S3DownloadService s3DownloadService;
    private final CsvAuditService csvAuditService;
    private final PersistenceAuditRecordPort persistenceAuditRecordPort;

    @Override
    public String start(String bucketName, String fileName) throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        String status = "PROCESSING";
        AuditRecord existing = persistenceAuditRecordPort.findByFileName(fileName);

        if (existing != null) {
            switch (existing.getStatus()) {
                case "COMPLETED" -> throw new IllegalStateException("File already processed");
                case "PROCESSING" -> throw new IllegalStateException("Job already running");
                case "FAILED" -> log.info("Restarting failed job");
            }
        }else{
            log.info("Starting new job");
        }
        Path localFile = s3DownloadService.downloadToTempFile(bucketName, fileName);

        csvAuditService.audit(bucketName, fileName, status);

        JobParameters parameters = new JobParametersBuilder().
                addString(ApplicationConstant.BUCKET_NAME, bucketName).
                addString(ApplicationConstant.FILE_NAME, fileName).
                addString(ApplicationConstant.LOCAL_FILE, localFile.toString()).toJobParameters();

        jobLauncher.run(batchJob, parameters);
        return ApplicationConstant.JOB_START;
    }
}
