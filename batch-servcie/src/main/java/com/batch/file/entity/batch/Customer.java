package com.batch.file.entity.batch;

import lombok.Data;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@DynamoDbBean
public class Customer {

    private String id;
    private String refNumber;
    private String firstName;
    private String lastName;
    private String gender;
    private BigDecimal amount;
    private LocalDate referenceDate;

    @DynamoDbPartitionKey
    public String getId() {
        return id;
    }

    public void generateId() {

        this.id = UUID.randomUUID().toString();
    }
}
