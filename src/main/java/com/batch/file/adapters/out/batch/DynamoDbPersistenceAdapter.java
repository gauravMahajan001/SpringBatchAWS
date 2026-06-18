package com.batch.file.adapters.out.batch;

import com.batch.file.constant.ApplicationConstant;
import com.batch.file.ports.out.batch.DynamoDbPersistencePort;
import com.batch.file.util.RecordPersistence;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
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
        recordPersistence.saveRecords(requests, dynamoDbClient, tableName);
    }




}
