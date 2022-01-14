package com.ubs.forex.validations.web;

import com.ubs.forex.validations.model.Transaction;
import com.ubs.forex.validations.model.ValidationResponse;
import com.ubs.forex.validations.validation.TransactionValidationService;
import io.micrometer.core.annotation.Timed;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "transactions", consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
public class TransactionController {

    private final TransactionValidationService transactionValidationService;

    @PostMapping("validate")
    @Operation(summary = "Validates transactions")
    @ApiResponse(responseCode = "200",
            description = "Validation errors. Transactions are valid if response list is empty.")
    @Timed(value = "validate_trades", percentiles = {0.95}, histogram = true)
    public List<ValidationResponse> validateTrades(@RequestBody List<Transaction> transactions) {
        return transactionValidationService.validate(transactions);
    }
}
