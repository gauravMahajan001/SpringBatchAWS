package com.batch.file.application.service.batch;

import com.batch.file.entity.batch.Customer;
import com.batch.file.ports.out.batch.DynamoDbPersistencePort;
import com.batch.file.util.RecordItem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.WriteRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public abstract class AbstractDynamoDbService {

    protected  final DynamoDbPersistencePort dynamoDbPersistencePort;
    protected  final int recordSize;
    protected  final RecordItem recordItem;
    protected  final String tableName;


    public void saveAll(Chunk<? extends Customer> chunk, String reason) {
        List<WriteRequest> requests = new ArrayList<>();

        for (Customer customer : chunk) {

            Map<String, AttributeValue> item = buildItem(customer, reason);
            WriteRequest writeRequest = recordItem.writeRequestForDB(item);
            requests.add(writeRequest);
            //Records process limit
            if (requests.size() == recordSize) {
                dynamoDbPersistencePort.saveAll(requests, tableName);
                requests.clear();
            }
        }
        //REMAINING RECORDS
        if (!requests.isEmpty()) {
            dynamoDbPersistencePort.saveAll(requests, tableName);
        }
    }

    protected abstract Map<String, AttributeValue> buildItem(Customer customer, String reason);
}
