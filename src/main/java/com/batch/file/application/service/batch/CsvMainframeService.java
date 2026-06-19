package com.batch.file.application.service.batch;

import com.batch.file.entity.batch.Customer;
import com.batch.file.exception.MainframeWriteException;
import com.batch.file.ports.out.batch.MainframePort;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CsvMainframeService {

    private final MainframePort mainframePort;
    private final CsvFailedService csvFailedService;

    @CircuitBreaker(name = "mainframeCircuitBreaker", fallbackMethod = "fallback")
    public void send(Chunk<? extends Customer> customer) {
        try {
            mainframePort.send(customer);
        } catch (Exception e) {
            throw new MainframeWriteException("Failed to write customer data to mainframe", e);
        }
    }

    public void fallback(Chunk<? extends Customer> chunk, Throwable ex) {
        try {
            log.error("Mainframe unavailable. Saving records to failed table. chunk size: {}", chunk.size(), ex);
            String reason = ex.getMessage() != null ? ex.getMessage() : ex.getClass().getSimpleName();
            csvFailedService.saveAll(chunk, reason);
        } catch (Exception saveException) {

            log.error("Failed to save records to failed table. chunk size: {}", chunk.size(), saveException);
            // retry mechanism will handle this exception and retry the operation
            throw saveException;
        }
    }
}

