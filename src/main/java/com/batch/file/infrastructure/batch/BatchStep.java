package com.batch.file.infrastructure.batch;

import com.batch.file.adapters.in.batch.BatchWriter;
import com.batch.file.constant.ApplicationConstant;
import com.batch.file.entity.batch.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import software.amazon.awssdk.services.dynamodb.model.ProvisionedThroughputExceededException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

@Slf4j
@Configuration
/*
 * BatchStep class is responsible for configuring the batch processing step in a Spring Batch job.
 * It defines the chunk size and retry count for processing Customer entities.
 * The step reads Customer data, processes it, and writes it to the specified output.
 */
public class BatchStep {
    @Value("${chunk.size}")
    private int chuckSize;
    @Value("${retry.count}")
    private int retryCount;

    @Bean
    public Step batchStep(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            ItemReader<Customer> reader,
            ItemProcessor<Customer, Customer> processor,
            BatchWriter writer) {

        log.info("Initializing batch step with configuration - chunkSize: {}, retryCount: {}", chuckSize, retryCount);
        return new StepBuilder(
                ApplicationConstant.CUSTOMER_STEP, jobRepository)
                .<Customer, Customer>chunk(chuckSize, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .faultTolerant()
                .retry(ProvisionedThroughputExceededException.class)
                .retry(SocketTimeoutException.class)
                .retry(ConnectException.class)
                .retryLimit(retryCount)
                .build();
    }
}