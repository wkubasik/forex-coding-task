package com.ubs.forex.validations.validation;

import com.ubs.forex.validations.utils.validators.TestValidator;
import com.ubs.forex.validations.validation.rules.ValidationRule;
import com.ubs.forex.validations.validation.rules.validators.Validator;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

public class TransactionValidatorFactoryTest {

    @Test
    void shouldGetValidatorsMap() {
        // given
        TestValidator testValidator1 = new TestValidator(ValidationRule.STYLE_AMERICAN_OR_EUROPEAN);
        TestValidator testValidator2 = new TestValidator(ValidationRule.SUPPORTED_COUNTERPARTIES);
        TransactionValidatorFactory factory = new TransactionValidatorFactory(Arrays.asList(
                testValidator1, testValidator2,
                new TestValidator(ValidationRule.LEGAL_ENTITY)
        ));

        // when
        List<Validator> validators = factory.getValidators(Arrays.asList(
                ValidationRule.STYLE_AMERICAN_OR_EUROPEAN,
                ValidationRule.SUPPORTED_COUNTERPARTIES));

        // then
        assertThat(validators, contains(testValidator1, testValidator2));
    }
}
