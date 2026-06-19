package com.batch.file.adapters.out.batch;

import com.batch.file.entity.batch.AuditRecord;
import com.batch.file.exception.AuditPersistenceException;
import com.batch.file.ports.out.batch.PersistenceAuditRecordPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;

@Slf4j
@Component
public class DynamoDbAuditAdapter implements PersistenceAuditRecordPort {

    private final DynamoDbTable<AuditRecord> auditRecordTable;

    public DynamoDbAuditAdapter(DynamoDbTable<AuditRecord> auditRecordTable) {
        this.auditRecordTable = auditRecordTable;
    }

    @Override
    public void save(AuditRecord auditRecord) {
        try {
            auditRecordTable.putItem(auditRecord);
        } catch (SdkClientException ex) {

            log.error("Unable to connect to DynamoDB while saving audit record. id={}", auditRecord.getId(), ex);
            throw new AuditPersistenceException("Unable to connect to DynamoDB", ex);

        } catch (DynamoDbException ex) {

            log.error("DynamoDB error while saving audit record. id={}", auditRecord.getId(), ex);
            throw new AuditPersistenceException("Failed to save audit record", ex);
        }


    }
}
