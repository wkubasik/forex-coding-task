package com.ubs.forex.validations.validation.rules.validators;

import com.ubs.forex.validations.model.Transaction;
import com.ubs.forex.validations.validation.rules.ValidationResult;
import com.ubs.forex.validations.validation.rules.ValidationRule;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class StyleAmericanOrEuropeanValidatorTest {

    private final StyleAmericanOrEuropeanValidator validator = new StyleAmericanOrEuropeanValidator();

    @Test
    void shouldReturnValidRule() {
        // given && when
        ValidationRule validationRule = validator.getValidationRule();

        // then
        assertThat(validationRule, equalTo(ValidationRule.STYLE_AMERICAN_OR_EUROPEAN));
    }

    @Test
    void shouldReturnSuccessForAmericanStyle() {
        //given
        Transaction transaction = new Transaction();
        transaction.setStyle("AMERICAN");

        // when
        ValidationResult result = validator.validate(transaction);

        // then
        assertThat(result.isSuccess(), equalTo(true));
        assertThat(result.getMessage(), nullValue());
    }

    @Test
    void shouldReturnSuccessForEuropeanStyle() {
        //given
        Transaction transaction = new Transaction();
        transaction.setStyle("EUROPEAN");

        // when
        ValidationResult result = validator.validate(transaction);

        // then
        assertThat(result.isSuccess(), equalTo(true));
        assertThat(result.getMessage(), nullValue());
    }

    @Test
    void shouldReturnFailForFrenchStyle() {
        //given
        Transaction transaction = new Transaction();
        transaction.setStyle("FRENCH");

        // when
        ValidationResult result = validator.validate(transaction);

        // then
        assertThat(result.isSuccess(), equalTo(false));
        assertThat(result.getMessage(), equalTo("Style must be one of the values: [AMERICAN, EUROPEAN]"));
    }
}
