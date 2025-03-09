package com.github.io.bank_wallet.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.io.bank_wallet.entity.Transaction;
import com.github.io.bank_wallet.enums.StatusType;
import com.github.io.bank_wallet.enums.TransactionType;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByWalletId(Long walletId);
    List<Transaction> findByTransactionType(TransactionType transactionType);
    List<Transaction> findByStatus(StatusType status);
}
