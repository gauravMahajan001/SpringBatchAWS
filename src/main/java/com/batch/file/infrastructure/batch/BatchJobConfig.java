package com.batch.file.infrastructure.batch;

import com.batch.file.constant.ApplicationConstant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.Step;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class BatchJobConfig {
    // Currently h2 data is used but need to use mysql or postgres
    private final JobRepository jobRepository;
    private final Step  batchStep;
    private final BatchJobListener  batchJobListener;

    @Bean
    public Job batchJob() {
        log.info("Initializing batch job: {}", ApplicationConstant.CUSTOMER_JOB);
        return new JobBuilder(ApplicationConstant.CUSTOMER_JOB, jobRepository)
                .start(batchStep)
                .listener(batchJobListener)
                .build();
    }
}
