package com.batch.file.util;

import com.batch.file.constant.ApplicationConstant;
import com.batch.file.domain.rule.CustomerValidation;
import com.batch.file.entity.batch.Customer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.PutRequest;
import software.amazon.awssdk.services.dynamodb.model.WriteRequest;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class RecordItem {

    private final CustomerValidation customerValidation;

    public Map<String, AttributeValue> buildItem(Customer customer) {

        Map<String, AttributeValue> item = new HashMap<>();


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

    public WriteRequest writeRequestForDB(Map<String, AttributeValue> item){
        PutRequest putRequest = PutRequest.builder().item(item).build();
        return   WriteRequest.builder().putRequest(putRequest).build();


    }

}
