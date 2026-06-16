package com.batch.file.adapters.out.batch;

import com.batch.file.constant.ApplicationConstant;
import com.batch.file.entity.Customer;
import com.batch.file.entity.FailedRecord;
import com.batch.file.ports.batch.out.FailedRecordPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

import java.time.LocalDateTime;


@Slf4j
@Component
@RequiredArgsConstructor
public class FailedRecordAdapter implements FailedRecordPort {

    private final DynamoDbEnhancedClient enhancedClient;

    /*
     * SAVE ENTIRE CHUNK
     */
    public void saveAll(
            Chunk<? extends Customer> chunk, String errorMsg) {

        DynamoDbTable<FailedRecord> table = enhancedClient.table(
                ApplicationConstant.TABLE_NAME_FAILED_RECORD,
                TableSchema.fromBean(
                        FailedRecord.class));

        for (Customer customer : chunk) {
            FailedRecord failedRecord =
                    buildFailedRecord(
                            customer,
                            errorMsg);
            table.putItem(failedRecord);
        }
        log.error(
                "Entire chunk saved to FAILED_RECORDS. Size: {}",
                chunk.size());
    }

    private FailedRecord buildFailedRecord(Customer customer,
                                           String errorMessage) {

        FailedRecord failedRecord =
                new FailedRecord();
        failedRecord.generateId();
        failedRecord.setRefNumber(
                customer.getRefNumber());
        failedRecord.setFirstName(
                customer.getFirstName());
        failedRecord.setLastName(
                customer.getLastName());
        failedRecord.setErrorMessage(
                errorMessage);
        failedRecord.setStatus(
                ApplicationConstant.PENDING_STATUS);
        failedRecord.setCreatedAt(
                LocalDateTime.now());
        return failedRecord;
    }
}
