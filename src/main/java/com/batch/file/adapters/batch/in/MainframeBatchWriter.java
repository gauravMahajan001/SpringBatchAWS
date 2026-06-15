package com.batch.file.adapters.batch.in;

import com.batch.file.application.service.batch.MainframeService;
import com.batch.file.entity.Customer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MainframeBatchWriter implements ItemWriter<Customer> {
   private final MainframeService mainframeService;
    @Override
    public void write(Chunk<? extends Customer> chunk) throws Exception {
        mainframeService.send(chunk);
    }
}
