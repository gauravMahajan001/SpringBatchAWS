package com.batch.file.util;

import com.batch.file.constant.ApplicationConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.BatchWriteItemRequest;
import software.amazon.awssdk.services.dynamodb.model.WriteRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class RecordPersistence {

    public void saveRecords(
            List<WriteRequest> requests, DynamoDbClient dynamoDbClient, String tableName) {

        Map<String, List<WriteRequest>> requestItems = new HashMap<>();

        requestItems.put(ApplicationConstant.TABLE_NAME, requests);
        BatchWriteItemRequest batchRequest = BatchWriteItemRequest.builder()
                .requestItems(requestItems).build();

        dynamoDbClient.batchWriteItem(batchRequest);

        log.info(
                "Inserted {} records into DynamoDB",
                requests.size());
    }


}
