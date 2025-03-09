package com.github.io.bank_wallet.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.github.io.bank_wallet.enums.StatusType;
import com.github.io.bank_wallet.enums.TransactionType;

import lombok.Builder;

@Builder
public record TransactionDto(Long id,Long walletId,BigDecimal amount,TransactionType transactionType,
    StatusType status,
    BigDecimal balanceAfterTransaction,
    LocalDateTime transactionDate) {
    
}
