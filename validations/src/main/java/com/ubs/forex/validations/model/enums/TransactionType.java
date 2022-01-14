package com.ubs.forex.validations.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
public enum TransactionType {
    SPOT("Spot"), FORWARD("Forward"), OPTION("VanillaOption");

    @Getter
    private final String value;

    public static TransactionType of(String value) {
        return Arrays.stream(TransactionType.values())
                .filter(type -> type.getValue().equals(value))
                .findFirst()
                .orElse(null);
    }
}
