package com.batch.file.application.service.batch;

import com.batch.file.constant.ApplicationConstant;
import com.batch.file.domain.rule.CustomerValidation;
import com.batch.file.entity.batch.Customer;
import com.batch.file.ports.out.batch.DynamoDbPersistencePort;
import com.batch.file.util.RecordItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.util.Map;

@Slf4j
@Service
public class CsvSaveService extends AbstractDynamoDbService {
    private final RecordItem recordItem;
    private final CustomerValidation customerValidation;

    public CsvSaveService(
            DynamoDbPersistencePort dynamoDbPersistencePort,
            RecordItem recordItem,
            @Value("${record.size}") int recordSize,
            @Value("${aws.dynamodb.customer-table-name}") String tableName, CustomerValidation customerValidation) {

        super(
                dynamoDbPersistencePort,
                recordSize,
                recordItem,
                tableName);
        this.recordItem = recordItem;
        this.customerValidation = customerValidation;
    }

    @Override
    protected Map<String, AttributeValue> buildItem(Customer customer, String reason) {
        Map<String, AttributeValue> item = recordItem.buildItem(customer);
        item.put("id", AttributeValue.builder().s(customer.getId()).build());
        item.put(ApplicationConstant.COLUMN, AttributeValue.builder().s(customerValidation.getValidationStatus(customer))
                .build());
        return item;
    }
}
