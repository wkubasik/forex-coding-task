package com.ubs.forex.validations.validation;

import com.ubs.forex.validations.validation.rules.TransactionRulesFactory;
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
    private final TransactionRulesFactory transactionRulesFactory;

    public TransactionValidatorFactory(List<Validator> validators, TransactionRulesFactory transactionRulesFactory) {
        this.validatorMap = validators.stream()
            .collect(Collectors.toMap(Validator::getValidationRule, Function.identity()));
        this.transactionRulesFactory = transactionRulesFactory;
    }

    public List<Validator> getValidators(String transactionType) {
        List<ValidationRule> rules = transactionRulesFactory.getValidationRules(transactionType);
        return rules.stream()
                .map(this.validatorMap::get)
                .collect(Collectors.toList());
    }
}
