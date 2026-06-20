package com.batch.file.adapters.out.batch;

import com.batch.file.exception.DynamoPersistenceException;
import com.batch.file.ports.out.batch.DynamoDbPersistencePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.BatchWriteItemRequest;
import software.amazon.awssdk.services.dynamodb.model.BatchWriteItemResponse;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;
import software.amazon.awssdk.services.dynamodb.model.WriteRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class DynamoDbPersistenceAdapter implements DynamoDbPersistencePort {
    
    private final DynamoDbClient dynamoDbClient;

    @Override
    public void saveAll(List<WriteRequest> requests, String tableName) {
        try {

            Map<String, List<WriteRequest>> requestItems = new HashMap<>();

            requestItems.put(tableName, requests);
            BatchWriteItemRequest batchRequest = BatchWriteItemRequest.builder().requestItems(requestItems).build();

            BatchWriteItemResponse response = dynamoDbClient.batchWriteItem(batchRequest);

            if (!response.unprocessedItems().isEmpty()) {

                log.warn("Unprocessed items found. table={}", tableName);
                throw new DynamoPersistenceException(String.format(
                        "DynamoDB returned unprocessed items for table %s",
                        tableName));
            }

            log.info("Inserted {} records into DynamoDB table={}", requests.size(), tableName);
        } catch (SdkClientException e) {

            log.error("Unable to connect to DynamoDB. table={}", tableName, e);
            throw new DynamoPersistenceException("Unable to connect to DynamoDB", e);

        } catch (DynamoDbException e) {

            log.error("DynamoDB operation failed. table={}", tableName, e);
            throw new DynamoPersistenceException("DynamoDB operation failed", e);
        }
    }
}
