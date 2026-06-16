package com.batch.file.domain.rule;

import com.batch.file.entity.batch.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerValidation {

    public  boolean isValid(Customer customer){
        return customer.getFirstName()!= null;

    }

    public String getValidationStatus(
            Customer customer) {

        return  isValid(customer)
                ? ValidationStatus.SUCCESS.name()
                : ValidationStatus.FAILED.name();
    }
}
