package com.batch.file.adapters.batch.in;

import com.batch.file.application.service.batch.DynamoDbService;
import com.batch.file.entity.Customer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DynamoBatchWriter implements ItemWriter<Customer> {

 private final DynamoDbService customerProcessingService;

    @Override
    public void write(Chunk<? extends Customer> chunk) {
        log.info("write chuck on dynamodb started");

        customerProcessingService.process(chunk);
        log.info("write chuck on dynamodb completed");
    }


}
