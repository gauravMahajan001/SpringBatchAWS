package com.batch.file.application.service.batch;

import com.batch.file.constant.ApplicationConstant;
import com.batch.file.entity.batch.Customer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CsvPersistenceService {

    private final CsvSaveService csvSaveService;
    private final CsvMainframeService mainframeService;
    //private final CsvFailedService failedRecordService;

    public void process(Chunk<? extends Customer> chunk){
        //framework handles db down and use retry mechanism
        log.info("write on DynamoDb started");
        csvSaveService.saveAll(chunk, null);
        log.info("write on DynamoDb completed");

        //NORMAL FLOW. if there is any exception then  retry and circuit breaker  mechanism
        log.info("write on mainframe started");
        mainframeService.send(chunk);
        log.info("write on mainframe completed");
    }

}
