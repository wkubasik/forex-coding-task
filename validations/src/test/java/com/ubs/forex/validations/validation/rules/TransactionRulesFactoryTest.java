package com.ubs.forex.validations.validation.rules;

import com.ubs.forex.validations.utils.ListUtils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

public class TransactionRulesFactoryTest {

    private final TransactionRulesFactory factory = new TransactionRulesFactory();

    @ParameterizedTest
    @MethodSource("data")
    void shouldReturnCorrectValidationRulesForSpotType(String type, ValidationRule... expectedValidationRules) {
        // given
        ValidationRule[] commonValidationRules = {
                ValidationRule.VALUE_DATE_CANNOT_BE_BEFORE_TRADE_DATE,
                ValidationRule.SUPPORTED_COUNTERPARTIES,
                ValidationRule.LEGAL_ENTITY
        };
        ValidationRule[] allExpectedRules = ListUtils.concatArrays(commonValidationRules, expectedValidationRules);

        // when
        List<ValidationRule> result = factory.getValidationRules(type);

        // then
        assertThat(result, contains(allExpectedRules));
    }

    private static Stream<Arguments> data() {
        return Stream.of(
          Arguments.of("Spot", getRulesArray(ValidationRule.VALUE_DATE_AGAINST_SPOT_TYPE)),
          Arguments.of("Forward", getRulesArray(ValidationRule.VALUE_DATE_AGAINST_FORWARD_TYPE)),
          Arguments.of("VanillaOption", getRulesArray(ValidationRule.STYLE_AMERICAN_OR_EUROPEAN))
        );
    }

    private static ValidationRule[] getRulesArray(ValidationRule... expectedValidationRules) {
        return expectedValidationRules;
    }
}
