package com.ubs.forex.validations.utils.validators;

import com.ubs.forex.validations.model.Transaction;
import com.ubs.forex.validations.validation.rules.ValidationResult;
import com.ubs.forex.validations.validation.rules.ValidationRule;
import com.ubs.forex.validations.validation.rules.validators.Validator;

public class TestValidator implements Validator {

    private final ValidationRule validationRule;
    private final ValidationResult result;

    public TestValidator(ValidationRule validationRule, ValidationResult result) {
        this.validationRule = validationRule;
        this.result = result;
    }

    public TestValidator(ValidationRule validationRule) {
        this.validationRule = validationRule;
        this.result = null;
    }

    @Override
    public ValidationResult validate(Transaction transaction) {
        return result;
    }

    @Override
    public ValidationRule getValidationRule() {
        return validationRule;
    }
}
