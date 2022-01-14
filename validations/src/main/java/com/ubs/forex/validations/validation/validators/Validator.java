package com.ubs.forex.validations.validation.validators;

import com.ubs.forex.validations.model.Transaction;

public interface Validator {
    ValidationResult validate(Transaction transaction);
    ValidationRule getValidationRule();
}
