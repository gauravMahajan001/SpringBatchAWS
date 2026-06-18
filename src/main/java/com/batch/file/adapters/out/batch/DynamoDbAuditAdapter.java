package com.batch.file.adapters.out.batch;

import com.batch.file.entity.batch.AuditRecord;
import com.batch.file.ports.out.batch.PersistenceAuditRecordPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;

@Slf4j
@Component
public class DynamoDbAuditAdapter implements PersistenceAuditRecordPort {

    private final DynamoDbTable<AuditRecord> auditRecordTable;

   public DynamoDbAuditAdapter(@Qualifier("auditRecordTable") DynamoDbTable<AuditRecord> auditRecordTable) {
        this.auditRecordTable = auditRecordTable;
    }

    @Override
    public void save(AuditRecord auditRecord) {
        auditRecordTable.putItem(auditRecord);
    }
}
