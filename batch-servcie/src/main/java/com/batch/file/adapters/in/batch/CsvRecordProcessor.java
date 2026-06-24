package com.batch.file.adapters.in.batch;

import com.batch.file.entity.batch.Customer;
import lombok.extern.slf4j.Slf4j;

import org.springframework.batch.item.ItemProcessor;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
@Slf4j
@Component
public class CsvRecordProcessor implements ItemProcessor<Customer, Customer> {

    @Override
    public @Nullable Customer process(Customer customer) throws Exception {
        //generate primary key
        customer.generateId();
        return customer;
    }
}
