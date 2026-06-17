package com.batch.file.adapters.in.batch;

import com.batch.file.application.service.batch.CsvRecordService;
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

    private final CsvRecordService batchProcessingService;

    @Override
    public void write(Chunk<? extends Customer> chunk) {

        batchProcessingService.process(chunk);
    }
}

