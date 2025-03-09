package com.github.io.bank_wallet.service;

import java.math.BigDecimal;
import java.util.List;

import com.github.io.bank_wallet.dto.TransactionDto;
// import com.github.io.bank_wallet.enums.StatusType;
import com.github.io.bank_wallet.enums.TransactionType;

public interface TransactionService {
    TransactionDto createTransaction(Long walletId, BigDecimal amount, TransactionType transactionType);
    List<TransactionDto> getTransactionsByWalletId(Long walletId);
    List<TransactionDto> getTransactionsByTransactionType(TransactionType transactionType);
    // List<TransactionDto> getTransactionsByStatus(StatusType status);
}
