package com.batch.file.ports.out.batch;

import software.amazon.awssdk.services.dynamodb.model.WriteRequest;

import java.util.List;

public interface DynamoDbPersistencePort {
    void saveAll(List<WriteRequest> requests, String tableName);

}
