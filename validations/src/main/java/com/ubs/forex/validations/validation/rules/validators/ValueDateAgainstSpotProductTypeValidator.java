package com.ubs.forex.validations.validation.rules.validators;

import com.ubs.forex.validations.common.DateTimeService;
import com.ubs.forex.validations.model.Transaction;
import com.ubs.forex.validations.validation.rules.ValidationResult;
import com.ubs.forex.validations.validation.rules.ValidationRule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class ValueDateAgainstSpotProductTypeValidator implements Validator {

    private final DateTimeService dateTimeService;

    @Override
    public ValidationResult validate(Transaction transaction) {
        LocalDate valueDate = transaction.getValueDate();
        LocalDate currentDate = dateTimeService.getCurrentDate();

        if (valueDate == null || valueDate.isAfter(currentDate.plusDays(2))) {
            return ValidationResult.failed("Value date must be before two days from now. Current date: " + currentDate);
        }
        return ValidationResult.success();
    }

    @Override
    public ValidationRule getValidationRule() {
        return ValidationRule.VALUE_DATE_AGAINST_SPOT_TYPE;
    }
}
