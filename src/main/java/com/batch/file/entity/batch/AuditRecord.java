package com.batch.file.entity.batch;

import lombok.Data;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

import java.time.LocalDate;
import java.util.UUID;

@Data
@DynamoDbBean
public class AuditRecord {
    private String id;
    private String bucketName;
    private String fileName;
    private LocalDate createdDate;


    @DynamoDbPartitionKey
    public String getId() {
        return id;
    }

    public void generateId() {
        this.id = UUID.randomUUID()
                .toString();
    }
}
