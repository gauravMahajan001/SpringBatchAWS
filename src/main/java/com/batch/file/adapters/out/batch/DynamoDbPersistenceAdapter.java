package com.batch.file.adapters.out.batch;

import com.batch.file.util.RecordPersistence;
import com.batch.file.exception.DynamoPersistenceException;
import com.batch.file.ports.out.batch.DynamoDbPersistencePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;
import software.amazon.awssdk.services.dynamodb.model.WriteRequest;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class DynamoDbPersistenceAdapter implements DynamoDbPersistencePort {
    private final RecordPersistence recordPersistence;
    private final DynamoDbClient dynamoDbClient;

    @Override
    public void saveAll(List<WriteRequest> requests, String tableName) {
        try {
            recordPersistence.saveRecords(requests, dynamoDbClient, tableName);
        } catch (SdkClientException e) {

            log.error("Unable to connect to DynamoDB. table={}", tableName, e);
            throw new DynamoPersistenceException("Unable to connect to DynamoDB", e);

        } catch (DynamoDbException e) {

            log.error("DynamoDB operation failed. table={}", tableName, e);
            throw new DynamoPersistenceException("DynamoDB operation failed", e);
        }
    }
}
