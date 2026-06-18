package com.batch.file.application.service.batch;

import com.batch.file.entity.batch.AuditRecord;
import com.batch.file.ports.out.batch.PersistenceAuditRecordPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class CsvAuditService {
    private final PersistenceAuditRecordPort saveAuditRecordPort;

    public void audit(String bucketName, String fileName) {

        AuditRecord auditRecord = new AuditRecord();
        auditRecord.generateId();
        auditRecord.setBucketName(bucketName);
        auditRecord.setFileName(fileName);
        auditRecord.setCreatedDate(LocalDate.now());

        saveAuditRecordPort.save(auditRecord);
    }
}
