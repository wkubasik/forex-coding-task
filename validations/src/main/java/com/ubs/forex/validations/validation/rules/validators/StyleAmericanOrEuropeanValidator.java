package com.ubs.forex.validations.validation.rules.validators;

import com.ubs.forex.validations.model.Transaction;
import com.ubs.forex.validations.validation.rules.ValidationResult;
import com.ubs.forex.validations.validation.rules.ValidationRule;
import org.hibernate.validator.internal.util.CollectionHelper;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class StyleAmericanOrEuropeanValidator implements Validator {

    private static final Set<String> ALLOWED_STYLES = CollectionHelper.asSet("AMERICAN", "EUROPEAN");

    @Override
    public ValidationResult validate(Transaction transaction) {
        String style = transaction.getStyle();
        if (style == null || !ALLOWED_STYLES.contains(style)) {
            return ValidationResult.failed("Style must be one of the values: " + ALLOWED_STYLES);
        }
        return ValidationResult.success();
    }

    @Override
    public ValidationRule getValidationRule() {
        return ValidationRule.STYLE_AMERICAN_OR_EUROPEAN;
    }
}
