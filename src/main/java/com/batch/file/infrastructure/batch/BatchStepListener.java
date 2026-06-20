package com.batch.file.infrastructure.batch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BatchStepListener implements StepExecutionListener {

    @Override
    public ExitStatus afterStep(
            StepExecution stepExecution) {

        if (stepExecution.getStatus() == BatchStatus.FAILED) {

            log.error(
                    "Step failed. Read={}, Write={}, Skip={}",
                    stepExecution.getReadCount(),
                    stepExecution.getWriteCount(),
                    stepExecution.getSkipCount());
        }
        return stepExecution.getExitStatus();
    }
}