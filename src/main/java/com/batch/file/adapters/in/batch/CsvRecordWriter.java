package com.batch.file.adapters.in.batch;

import com.batch.file.application.service.batch.CsvPersistenceService;
import com.batch.file.entity.batch.Customer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CsvRecordWriter implements ItemWriter<Customer> {

    private final CsvPersistenceService csvRecordService;

    @Override
    public void write(Chunk<? extends Customer> chunk) {

        csvRecordService.process(chunk);
    }
}

