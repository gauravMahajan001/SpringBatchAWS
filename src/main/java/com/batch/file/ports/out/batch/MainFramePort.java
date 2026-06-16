package com.batch.file.ports.out.batch;

import com.batch.file.entity.Customer;
import org.springframework.batch.item.Chunk;

public interface MainFramePort {
    void send(Chunk<? extends Customer> chunk) throws Exception;
}
