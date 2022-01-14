package com.ubs.forex.validations.validation;

import com.ubs.forex.validations.model.Transaction;
import com.ubs.forex.validations.model.ValidationResponse;
import com.ubs.forex.validations.validation.rules.ValidationResult;
import com.ubs.forex.validations.validation.rules.validators.CompositeValidator;
import com.ubs.forex.validations.validation.rules.validators.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
            List<Validator> validators = transactionValidatorFactory.getValidators(transaction.getType());
            List<ValidationResult> validationResults = getValidationResults(transaction, validators);
            validationResponses.addAll(mapResultsToValidationMessage(validationResults, index));
            index++;
        }

        return validationResponses;
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
