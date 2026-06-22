package com.batch.file.infrastructure.batch;

import com.batch.file.application.service.batch.S3DownloadService;
import com.batch.file.entity.batch.AuditRecord;
import com.batch.file.ports.out.batch.PersistenceAuditRecordPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Slf4j
@Component
@RequiredArgsConstructor
public class BatchJobListener implements JobExecutionListener {
    private final PersistenceAuditRecordPort persistenceAuditRecordPort;

    @Override
    public void afterJob(JobExecution jobExecution) {
        String fileName = jobExecution.getJobParameters().getString("fileName");
        AuditRecord audit = persistenceAuditRecordPort.findByFileName(fileName);

        if (audit == null) {
            return;
        }

        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            audit.setStatus("COMPLETED");
        } else {
            audit.setStatus("FAILED");
        }
        audit.setUpdatedDate(LocalDate.now());
        persistenceAuditRecordPort.save(audit);
    }
}
