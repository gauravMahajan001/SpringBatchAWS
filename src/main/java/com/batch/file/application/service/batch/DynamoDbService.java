package com.batch.file.application.service.batch;

import com.batch.file.entity.batch.Customer;
import com.batch.file.ports.out.batch.DynamoDbPort;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DynamoDbService {
    private final DynamoDbPort dynamoDbPort;

    public void process(Chunk<? extends Customer> chunk) {
        dynamoDbPort.saveAll(chunk);
    }
}
