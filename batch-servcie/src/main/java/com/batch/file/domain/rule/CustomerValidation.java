package com.batch.file.domain.rule;

import com.batch.file.entity.batch.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CustomerValidation {

    public  boolean isValid(Customer customer){
        boolean isValid = customer.getFirstName() != null;
        log.trace("Customer validation result - ID: {}, FirstName: {}, Valid: {}", customer.getId(), customer.getFirstName(), isValid);
        return isValid;

    }

    public String getValidationStatus(
            Customer customer) {

        String status = isValid(customer)
                ? ValidationStatus.SUCCESS.name()
                : ValidationStatus.FAILED.name();
        log.debug("Customer validation status - ID: {}, Status: {}", customer.getId(), status);
        return status;
    }
}
