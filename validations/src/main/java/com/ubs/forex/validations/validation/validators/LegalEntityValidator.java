package com.ubs.forex.validations.validation.validators;

import com.ubs.forex.validations.model.Transaction;
import org.springframework.stereotype.Component;

@Component
public class LegalEntityValidator implements Validator {

    private static final String ALLOWED_LEGAL_ENTITY = "UBS AG";

    @Override
    public ValidationResult validate(Transaction transaction) {
        String legalEntity = transaction.getLegalEntity();
        if (!ALLOWED_LEGAL_ENTITY.equals(legalEntity)) {
            return ValidationResult.failed("Legal entity must equal: " + ALLOWED_LEGAL_ENTITY);
        }
        return ValidationResult.success();
    }

    @Override
    public ValidationRule getValidationRule() {
        return ValidationRule.LEGAL_ENTITY;
    }
}
