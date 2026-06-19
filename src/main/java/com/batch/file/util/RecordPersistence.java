package com.batch.file.util;

import com.batch.file.exception.DynamoPersistenceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.BatchWriteItemRequest;
import software.amazon.awssdk.services.dynamodb.model.BatchWriteItemResponse;
import software.amazon.awssdk.services.dynamodb.model.WriteRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class RecordPersistence {

    public void saveRecords(List<WriteRequest> requests, DynamoDbClient dynamoDbClient, String tableName) {

        Map<String, List<WriteRequest>> requestItems = new HashMap<>();

        requestItems.put(tableName, requests);
        BatchWriteItemRequest batchRequest = BatchWriteItemRequest.builder().requestItems(requestItems).build();

        BatchWriteItemResponse response = dynamoDbClient.batchWriteItem(batchRequest);

        if (!response.unprocessedItems().isEmpty()) {

            log.warn("Unprocessed items found. table={}", tableName);
            throw new DynamoPersistenceException("DynamoDB returned unprocessed items");
        }

        log.info("Inserted {} records into DynamoDB table={}", requests.size(), tableName);
    }


}
