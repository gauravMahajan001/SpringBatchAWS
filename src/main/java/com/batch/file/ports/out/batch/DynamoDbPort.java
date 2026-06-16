package com.batch.file.ports.out.batch;

import com.batch.file.entity.batch.Customer;
import org.springframework.batch.item.Chunk;

public interface DynamoDbPort {
    void saveAll(Chunk<? extends Customer> chunk);

}
