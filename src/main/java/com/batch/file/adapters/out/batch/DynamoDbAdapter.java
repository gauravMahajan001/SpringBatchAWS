package com.batch.file.adapters.out.batch;

import com.batch.file.constant.ApplicationConstant;
import com.batch.file.domain.rule.CustomerValidation;
import com.batch.file.entity.batch.Customer;
import com.batch.file.ports.out.batch.DynamoDbPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.BatchWriteItemRequest;
import software.amazon.awssdk.services.dynamodb.model.PutRequest;
import software.amazon.awssdk.services.dynamodb.model.WriteRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class DynamoDbAdapter implements DynamoDbPort {
    private final DynamoDbClient dynamoDbClient;
    private final CustomerValidation customerValidation;
    @Value("${record.size}")
    private int recordSize;

    @Override
    public void saveAll(Chunk<? extends Customer> chunk) {
        List<WriteRequest> requests = new ArrayList<>();

        for (Customer customer : chunk) {
            Map<String, AttributeValue> item = buildItem(customer);

            PutRequest putRequest = PutRequest.builder().item(item).build();
            WriteRequest writeRequest = WriteRequest.builder().putRequest(putRequest).build();
            requests.add(writeRequest);
            //DynamoDB batch limit
            if (requests.size() == recordSize) {
                writeBatch(requests);
                requests.clear();
            }
        }
        //REMAINING RECORDS
        if (!requests.isEmpty()) {
            writeBatch(requests);
        }
    }

    private Map<String, AttributeValue> buildItem(Customer customer) {

        Map<String, AttributeValue> item = new HashMap<>();

        item.put("id", AttributeValue.builder().s(customer.getId()).build());
        item.put("refNumber", AttributeValue.builder().s(customer.getRefNumber()).build());
        item.put("firstName", AttributeValue.builder().s(customer.getFirstName()).build());
        item.put("lastName", AttributeValue.builder().s(customer.getLastName()).build());
        item.put("gender", AttributeValue.builder().s(customer.getGender()).build());

        if (customer.getAmount() != null) {
            item.put("amount", AttributeValue.builder().n(customer.getAmount().toString()).build());
        }

        if (customer.getReferenceDate() != null) {
            item.put("referenceDate", AttributeValue.builder().s(customer.getReferenceDate().toString())
                    .build());
        }

        item.put(ApplicationConstant.COLUMN, AttributeValue.builder().s(customerValidation.getValidationStatus(customer))
                .build());
        return item;
    }

    private void writeBatch(
            List<WriteRequest> requests) {

        Map<String, List<WriteRequest>> requestItems = new HashMap<>();

        requestItems.put(ApplicationConstant.TABLE_NAME, requests);
        BatchWriteItemRequest batchRequest = BatchWriteItemRequest.builder()
                .requestItems(requestItems).build();

        dynamoDbClient.batchWriteItem(batchRequest);

        log.info(
                "Inserted {} records into DynamoDB",
                requests.size());
    }

}
