package com.ubs.forex.validations.validation.rules.validators;

import com.ubs.forex.validations.model.Transaction;
import com.ubs.forex.validations.validation.rules.ValidationResult;
import com.ubs.forex.validations.validation.rules.ValidationRule;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class ValueDateCannotBeBeforeTradeDateValidator implements Validator {

    @Override
    public ValidationResult validate(Transaction transaction) {
        LocalDate valueDate = transaction.getValueDate();
        LocalDate tradeDate = transaction.getTradeDate();
        if (ObjectUtils.allNotNull(valueDate, tradeDate)
                && valueDate.isBefore(tradeDate)) {
            return ValidationResult.failed("Value date cannot be before trade date.");
        }
        return ValidationResult.success();
    }

    @Override
    public ValidationRule getValidationRule() {
        return ValidationRule.VALUE_DATE_CANNOT_BE_BEFORE_TRADE_DATE;
    }
}
