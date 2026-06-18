package com.batch.file.ports.out.batch;

import com.batch.file.entity.batch.AuditRecord;

public interface PersistenceAuditRecordPort {
    void save(AuditRecord auditRecord);

}
