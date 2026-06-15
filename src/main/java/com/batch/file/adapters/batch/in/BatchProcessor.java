package com.batch.file.adapters.batch.in;

import com.batch.file.entity.Customer;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
@Slf4j
@Component
public class BatchProcessor implements ItemProcessor<Customer, Customer> {

    @Override
    public @Nullable Customer process(Customer customer) throws Exception {
        //generate primary key
        customer.generateId();
        return customer;
    }
}
