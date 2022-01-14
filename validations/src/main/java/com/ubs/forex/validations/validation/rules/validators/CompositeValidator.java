package com.ubs.forex.validations.validation.rules.validators;

import com.ubs.forex.validations.model.Transaction;
import com.ubs.forex.validations.validation.rules.ValidationResult;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class CompositeValidator {

    private final List<Validator> validators;

    public List<ValidationResult> validate(Transaction transaction) {
        return validators.stream()
                .map(v -> v.validate(transaction))
                .collect(Collectors.toList());
    }
}
