package com.ubs.forex.validations.validation;

import com.ubs.forex.validations.model.Transaction;
import com.ubs.forex.validations.model.ValidationResponse;
import com.ubs.forex.validations.utils.validators.TestValidator;
import com.ubs.forex.validations.validation.rules.TransactionRulesFactory;
import com.ubs.forex.validations.validation.rules.ValidationResult;
import com.ubs.forex.validations.validation.rules.ValidationRule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class TransactionValidationServiceTest {

    @InjectMocks
    private TransactionValidationService service;

    @Mock
    private TransactionValidatorFactory transactionValidatorFactory;

    @Mock
    private TransactionRulesFactory transactionRulesFactory;

    @Test
    void shouldValidateTransactionsAgainstTheRules() {
        // given
        TestValidator testValidator1 = new TestValidator(ValidationRule.STYLE_AMERICAN_OR_EUROPEAN,
                new ValidationResult(true, null));
        TestValidator testValidator2 = new TestValidator(ValidationRule.SUPPORTED_COUNTERPARTIES,
                new ValidationResult(false, "Error message"));
        List<Transaction> transactions = Arrays.asList(getTransaction("FORWARD"), getTransaction("SPOT"));

        List<ValidationRule> validationRules1 = Collections.singletonList(ValidationRule.STYLE_AMERICAN_OR_EUROPEAN);
        given(transactionRulesFactory.getValidationRules("FORWARD"))
                .willReturn(validationRules1);
        List<ValidationRule> validationRules2 = Collections.singletonList(ValidationRule.SUPPORTED_COUNTERPARTIES);
        given(transactionRulesFactory.getValidationRules("SPOT"))
                .willReturn(validationRules2);

        given(transactionValidatorFactory.getValidators(validationRules1))
                .willReturn(Collections.singletonList(testValidator1));
        given(transactionValidatorFactory.getValidators(validationRules2))
                .willReturn(Collections.singletonList(testValidator2));

        // when
        List<ValidationResponse> result = service.validate(transactions);

        // then
        assertThat(result.size(), equalTo(1));
        assertThat(result.get(0).getTransactionIndex(), equalTo(1));
        assertThat(result.get(0).getMessage(), equalTo("Error message"));
    }

    private Transaction getTransaction(String type) {
        Transaction transaction = new Transaction();
        transaction.setType(type);
        return transaction;
    }
}
