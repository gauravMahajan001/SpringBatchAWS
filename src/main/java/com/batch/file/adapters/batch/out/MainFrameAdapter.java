package com.batch.file.adapters.batch.out;

import com.batch.file.domain.rule.CustomerValidation;
import com.batch.file.entity.Customer;
import com.batch.file.ports.batch.out.MainFramePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MainFrameAdapter implements MainFramePort {
  private final JdbcBatchItemWriter<Customer> jdbcBatchItemWriter;
  private final CustomerValidation customerValidation;

    @Override
    public void send(Chunk<? extends Customer> chunk) throws Exception {
        log.info("write chuck on mainFrame started");
        Chunk<Customer> validChunk = new Chunk<>();
        for (Customer customer : chunk) {

            if (!customerValidation.isValid(customer)) {
                log.warn(
                        "Skipping Mainframe write. Invalid customer id: {}",
                        customer.getId());
                continue;
            }
            validChunk.add(customer);
        }
        // write only once after validation
        if (!validChunk.isEmpty()) {
            jdbcBatchItemWriter.write(validChunk);
        }
        log.info("write chuck on dynamodb completed");
    }
}
