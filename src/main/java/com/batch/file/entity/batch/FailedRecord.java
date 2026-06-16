package com.batch.file.entity.batch;

import lombok.Data;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@DynamoDbBean
public class FailedRecord {

    private String id;
    private String refNumber;
    private String firstName;
    private String lastName;
    private String gender;
    private BigDecimal amount;
    private LocalDate referenceDate;
    private String errorMessage;
    private String status;
    private LocalDateTime createdAt;

    @DynamoDbPartitionKey
    public String getId() {
        return id;
    }

    public void generateId() {
        this.id = UUID.randomUUID()
                .toString();
    }
}
