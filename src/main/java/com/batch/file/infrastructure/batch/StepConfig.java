package com.batch.file.infrastructure.batch;

import com.batch.file.adapters.batch.in.CustomerBatchWriter;
import com.batch.file.constant.ApplicationConstant;
import com.batch.file.entity.Customer;
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

@Configuration
public class StepConfig {
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
            CustomerBatchWriter writer) {

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