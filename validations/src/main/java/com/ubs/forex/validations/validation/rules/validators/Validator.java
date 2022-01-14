package com.ubs.forex.validations.validation.rules.validators;

import com.ubs.forex.validations.model.Transaction;
import com.ubs.forex.validations.validation.rules.ValidationResult;
import com.ubs.forex.validations.validation.rules.ValidationRule;

public interface Validator {
    ValidationResult validate(Transaction transaction);
    ValidationRule getValidationRule();
}
