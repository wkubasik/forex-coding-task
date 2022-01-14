package com.ubs.forex.validations.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ValidationResponse {

    @Schema(example = "Here goes the error message", description = "Detailed error message")
    private final String message;

    @Schema(example = "0", description = "Indicates the transaction index from the request body. Zero-based.")
    private final int transactionIndex;
}
