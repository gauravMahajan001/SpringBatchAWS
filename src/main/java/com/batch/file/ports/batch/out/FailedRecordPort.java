package com.batch.file.ports.batch.out;

import com.batch.file.entity.Customer;
import org.springframework.batch.item.Chunk;

public interface FailedRecordPort {
    void saveAll(
            Chunk<? extends Customer> chunk,
            String errorMessage);
}
