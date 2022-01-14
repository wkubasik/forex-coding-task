package com.ubs.forex.validations.model;

import com.ubs.forex.validations.model.enums.TransactionDirection;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class Transaction {

    @Schema(example = "YODA1", allowableValues = {"YODA1", "YODA2"})
    private String customer;

    @Schema(example = "EURUSD")
    private String ccyPair;

    @Schema(example = "Spot", allowableValues = {"Spot", "Forward", "VanillaOption"})
    private String type;

    @Schema(example = "SELL", allowableValues = {"SELL", "BUY"})
    private TransactionDirection direction;
    private LocalDate tradeDate;
    @Schema(example = "1000000")
    private BigDecimal amount1;

    @Schema(example = "1120000")
    private BigDecimal amount2;

    @Schema(example = "1.12")
    private BigDecimal rate;

    @Schema(example = "UBS AG", allowableValues = {"UBS AG"})
    private String legalEntity;

    @Schema(example = "James Doe")
    private String trader;
    private LocalDate valueDate;

    @Schema(example = "EUROPEAN", allowableValues = {"AMERICAN", "EUROPEAN"})
    private String style;

    @Schema(example = "CALL")
    private String strategy;
    private LocalDate deliveryDate;
    private LocalDate expiryDate;
    private LocalDate excerciseStartDate;

    @Schema(example = "USD")
    private String payCcy;

    @Schema(example = "0.2")
    private BigDecimal premium;

    @Schema(example = "USD")
    private String premiumCcy;

    @Schema(example = "%USD")
    private String premiumType;
    private LocalDate premiumDate;
}
