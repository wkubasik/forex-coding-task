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
public class ValueDateAgainstForwardProductTypeValidator implements Validator {

    private final DateTimeService dateTimeService;

    @Override
    public ValidationResult validate(Transaction transaction) {
        LocalDate valueDate = transaction.getValueDate();
        LocalDate currentDate = dateTimeService.getCurrentDate();

        if (valueDate == null || valueDate.isBefore(currentDate.plusDays(3))) {
            return ValidationResult.failed("Value date must be after greater than 2 days from now. Current date: "
                    + currentDate);
        }
        return ValidationResult.success();
    }

    @Override
    public ValidationRule getValidationRule() {
        return ValidationRule.VALUE_DATE_AGAINST_FORWARD_TYPE;
    }
}
