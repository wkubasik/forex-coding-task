package com.ubs.forex.validations.validation.validators;

import com.ubs.forex.validations.model.Transaction;
import org.hibernate.validator.internal.util.CollectionHelper;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class SupportedCounterpartiesValidator implements Validator {

    private static final Set<String> SUPPORTED_COUNTERPARTIES = CollectionHelper.asSet("YODA1", "YODA2");

    @Override
    public ValidationResult validate(Transaction transaction) {
        String customer = transaction.getCustomer();
        if (customer == null || !SUPPORTED_COUNTERPARTIES.contains(customer)) {
            return ValidationResult.failed("Customer must be one of the supported counterparties: "
                    + SUPPORTED_COUNTERPARTIES);
        }
        return ValidationResult.success();
    }

    @Override
    public ValidationRule getValidationRule() {
        return ValidationRule.SUPPORTED_COUNTERPARTIES;
    }
}
