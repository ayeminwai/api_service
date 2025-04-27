package com.maybank.apiservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionRequest {
    @NotNull
    private Long accountId;

    @NotNull
    private BigDecimal amount;

    @NotBlank
    private String txnType; // DEPOSIT or WITHDRAWAL
}
