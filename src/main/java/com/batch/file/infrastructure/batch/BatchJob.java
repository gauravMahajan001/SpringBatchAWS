package com.batch.file.infrastructure.batch;

import com.batch.file.constant.ApplicationConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.Step;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BatchJob {

    private final JobRepository jobRepository;
    private final Step stepConfig;

    @Bean
    public Job batchJob() {
        return new JobBuilder(ApplicationConstant.CUSTOMER_JOB, jobRepository)
                .start(stepConfig)
                .build();
    }
}
