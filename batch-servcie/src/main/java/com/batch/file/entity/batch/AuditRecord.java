package com.batch.file.entity.batch;

import lombok.Data;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Data
@DynamoDbBean
public class AuditRecord {

    private String bucketName;
    private String fileName;
    private String status;
    private LocalDate createdDate;
    private LocalDate updatedDate;

    @DynamoDbPartitionKey
    public String getFileName() {
        return fileName;
    }
}
