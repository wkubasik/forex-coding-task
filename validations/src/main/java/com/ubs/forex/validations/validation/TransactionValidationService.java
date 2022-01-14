package com.ubs.forex.validations.validation;

import com.ubs.forex.validations.model.Transaction;
import com.ubs.forex.validations.model.ValidationResponse;
import com.ubs.forex.validations.model.enums.TransactionType;
import com.ubs.forex.validations.validation.validators.CompositeValidator;
import com.ubs.forex.validations.validation.validators.ValidationResult;
import com.ubs.forex.validations.validation.validators.ValidationRule;
import com.ubs.forex.validations.validation.validators.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionValidationService {

    private final TransactionValidatorFactory transactionValidatorFactory;

    public List<ValidationResponse> validate(List<Transaction> transactions) {
        List<ValidationResponse> validationResponses = new ArrayList<>();

        int index = 0;
        for (Transaction transaction : transactions) {
            List<ValidationRule> rules = getValidationRules(transaction);
            List<Validator> validators = transactionValidatorFactory.getValidators(rules);
            List<ValidationResult> validationResults = getValidationResults(transaction, validators);
            validationResponses.addAll(mapResultsToValidationMessage(validationResults, index));
            index++;
        }

        return validationResponses;
    }

    private List<ValidationRule> getValidationRules(Transaction transaction) {
        List<ValidationRule> validators = new ArrayList<>(Arrays.asList(
                ValidationRule.VALUE_DATE_CANNOT_BE_BEFORE_TRADE_DATE,
                ValidationRule.SUPPORTED_COUNTERPARTIES,
                ValidationRule.LEGAL_ENTITY
        ));

        TransactionType transactionType = TransactionType.of(transaction.getType());
        if (TransactionType.SPOT == transactionType) {
            validators.add(ValidationRule.VALUE_DATE_AGAINST_SPOT_TYPE);
        } else if (TransactionType.FORWARD == transactionType) {
            validators.add(ValidationRule.VALUE_DATE_AGAINST_FORWARD_TYPE);
        } else if (TransactionType.OPTION == transactionType) {
            validators.add(ValidationRule.STYLE_AMERICAN_OR_EUROPEAN);
        }

        return validators;
    }

    private List<ValidationResult> getValidationResults(Transaction transaction,
                                                        List<Validator> validators) {
        CompositeValidator compositeValidator = new CompositeValidator(validators);
        return compositeValidator.validate(transaction);
    }

    private List<ValidationResponse> mapResultsToValidationMessage(List<ValidationResult> validationResults,
                                                                   int index) {
        return validationResults.stream()
                .filter(result -> !result.isSuccess())
                .map(result -> new ValidationResponse(result.getMessage(), index))
                .collect(Collectors.toList());
    }
}
