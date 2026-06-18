package com.batch.file.application.service.batch;

import com.batch.file.entity.batch.Customer;
import com.batch.file.entity.batch.FailedRecord;
import com.batch.file.ports.out.batch.DynamoDbPersistencePort;
import com.batch.file.util.RecordItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.util.Map;

@Slf4j
@Service
public class CsvFailedService extends AbstractDynamoDbService {
    private final RecordItem recordItem;

    public CsvFailedService(
            DynamoDbPersistencePort dynamoDbPersistencePort,
            RecordItem recordItem,
            @Value("${record.size}") int recordSize,
            @Value("${failed.table.name}") String tableName) {

        super(
                dynamoDbPersistencePort,
                recordSize,
                recordItem,
                tableName);
        this.recordItem = recordItem;
    }

    @Override
    protected Map<String, AttributeValue> buildItem(Customer customer, String reason) {
        Map<String, AttributeValue> item = recordItem.buildItem(customer);
        FailedRecord failedRecord = new FailedRecord();
        item.put("id", AttributeValue.builder().s(failedRecord.getId()).build());
        item.put("reason", AttributeValue.builder().s(reason).build());
        return item;
    }
}
