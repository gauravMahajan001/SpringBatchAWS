package com.batch.file.application.service.batch;

import com.batch.file.entity.batch.Customer;
import com.batch.file.exception.MainFrameException;
import com.batch.file.ports.out.batch.MainframePort;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CsvMainframeService {

    private final CircuitBreakerRegistry circuitBreakerRegistry;
    private final MainframePort mainframePort;
    private final CsvFailedService failedRecordService;

    @Retry(name = "mainFrameRetry")
    @io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker(name = "mainFrameCircuitBreaker",
            fallbackMethod = "fallback")
    public void send(Chunk<? extends Customer> customer)  {
        try {
            mainframePort.send(customer);
        } catch (Exception e) {
            throw new MainFrameException(e);
        }
    }

    public void fallback(Chunk<? extends Customer> chunk, Exception ex) {

        failedRecordService.saveAll(chunk, ex.getMessage());
    }

    public boolean isUnavailable() {

        CircuitBreaker circuitBreaker = circuitBreakerRegistry
                        .circuitBreaker(
                                "mainFrameCircuitBreaker");
        return circuitBreaker.getState()
                == CircuitBreaker.State.OPEN;
    }
}
