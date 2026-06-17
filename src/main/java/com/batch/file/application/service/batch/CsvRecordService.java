package com.batch.file.application.service.batch;

import com.batch.file.constant.ApplicationConstant;
import com.batch.file.entity.batch.Customer;
import com.batch.file.ports.out.batch.DynamoDbPort;
import com.batch.file.ports.out.batch.FailedRecordPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CsvRecordService {

    private final DynamoDbPort dynamoDbPort;
    private final MainframeRecordService mainframeService;
    private final FailedRecordPort failedRecordPort;

    public void process(Chunk<? extends Customer> chunk){
        //framework handles db down and use retry mechanism
        log.info("write on DynamoDb started");
        dynamoDbPort.saveAll(chunk);
        log.info("write on DynamoDb completed");

        //CIRCUIT OPEN
        if (mainframeService.isUnavailable()) {
            log.warn("MainFrame Db down");
            failedRecordPort.saveAll(chunk, ApplicationConstant.MAINFRAME_DOWN);
            return;
        }

        //NORMAL FLOW. if there is any exception then  retry and circuit breaker  mechanism
        log.info("write on mainframe started");
        mainframeService.send(chunk);
        log.info("write on mainframe completed");
    }

}
