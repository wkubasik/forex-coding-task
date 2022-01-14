package com.ubs.forex.validations.validation;

import com.ubs.forex.validations.validation.rules.ValidationRule;
import com.ubs.forex.validations.validation.rules.validators.Validator;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class TransactionValidatorFactory {

    private final Map<ValidationRule, Validator> validatorMap;

    public TransactionValidatorFactory(List<Validator> validators) {
        this.validatorMap = validators.stream()
            .collect(Collectors.toMap(Validator::getValidationRule, Function.identity()));
    }

    public List<Validator> getValidators(List<ValidationRule> rules) {
        return rules.stream()
                .map(this.validatorMap::get)
                .collect(Collectors.toList());
    }
}
