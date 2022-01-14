package com.ubs.forex.validations.validation.validators;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public
class ValidationResult {
    private final boolean success;
    private final String message;

    public static ValidationResult success() {
        return new ValidationResult(true, null);
    }

    public static ValidationResult failed(String message) {
        return new ValidationResult(false, message);
    }
}
