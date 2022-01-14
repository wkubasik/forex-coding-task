package com.ubs.forex.validations.validation.rules;

import com.ubs.forex.validations.model.enums.TransactionType;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class TransactionRulesFactory {

    public List<ValidationRule> getValidationRules(String transactionType) {
        List<ValidationRule> validators = new ArrayList<>(Arrays.asList(
                ValidationRule.VALUE_DATE_CANNOT_BE_BEFORE_TRADE_DATE,
                ValidationRule.SUPPORTED_COUNTERPARTIES,
                ValidationRule.LEGAL_ENTITY
        ));

        TransactionType type = TransactionType.of(transactionType);
        if (TransactionType.SPOT == type) {
            validators.add(ValidationRule.VALUE_DATE_AGAINST_SPOT_TYPE);
        } else if (TransactionType.FORWARD == type) {
            validators.add(ValidationRule.VALUE_DATE_AGAINST_FORWARD_TYPE);
        } else if (TransactionType.OPTION == type) {
            validators.add(ValidationRule.STYLE_AMERICAN_OR_EUROPEAN);
        }

        return validators;
    }
}
