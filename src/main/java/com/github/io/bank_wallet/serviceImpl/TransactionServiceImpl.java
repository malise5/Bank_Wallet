package com.github.io.bank_wallet.serviceImpl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.github.io.bank_wallet.dto.TransactionDto;
import com.github.io.bank_wallet.entity.Transaction;
import com.github.io.bank_wallet.entity.Wallet;
import com.github.io.bank_wallet.enums.StatusType;
import com.github.io.bank_wallet.enums.TransactionType;
import com.github.io.bank_wallet.repository.TransactionRepository;
import com.github.io.bank_wallet.repository.UserRepository;
import com.github.io.bank_wallet.repository.WalletRepository;
import com.github.io.bank_wallet.service.TransactionService;

@Service
public class TransactionServiceImpl implements TransactionService {

    private UserRepository userRepository;
    private TransactionRepository transactionRepository;
    private WalletRepository walletRepository;

    public TransactionServiceImpl(UserRepository userRepository, TransactionRepository transactionRepository, WalletRepository walletRepository) {
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
        this.walletRepository = walletRepository;
    }

    @Override
    public TransactionDto createTransaction(Long walletId, BigDecimal amount, TransactionType transactionType) {
        Wallet wallet = walletRepository.findById(walletId).orElseThrow(() -> new RuntimeException("Wallet not found"));

        BigDecimal newBalance = transactionType == TransactionType.WITHDRAWAL ? wallet.getBalance().subtract(amount)
                : wallet.getBalance().add(amount);

        if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new RuntimeException("Insufficient balance");
        }

        wallet.setBalance(newBalance);
        walletRepository.save(wallet);

        Transaction transaction = transactionRepository.save(Transaction.builder()
                .wallet(wallet)
                .amount(amount)
                .transactionType(transactionType)
                .status(StatusType.COMPLETED)
                .balanceAfterTransaction(newBalance)
                .transactionDate(LocalDateTime.now())
                .build());

        return TransactionDto.builder()
                .id(transaction.getId())
                .walletId(transaction.getWallet().getId())
                .amount(transaction.getAmount())
                .transactionType(transaction.getTransactionType())
                .status(transaction.getStatus())
                .balanceAfterTransaction(transaction.getBalanceAfterTransaction())
                .transactionDate(transaction.getTransactionDate())
                .build();
    }

    @Override
    public List<TransactionDto> getTransactionsByWalletId(Long walletId) {
        return transactionRepository.findByWalletId(walletId).stream()
                .map(transaction -> TransactionDto.builder()
                        .id(transaction.getId())
                        .walletId(transaction.getWallet().getId())
                        .amount(transaction.getAmount())
                        .transactionType(transaction.getTransactionType())
                        .status(transaction.getStatus())
                        .balanceAfterTransaction(transaction.getBalanceAfterTransaction())
                        .transactionDate(transaction.getTransactionDate())
                        .build())
                .toList();
    }

    @Override
    public List<TransactionDto> getTransactionsByTransactionType(TransactionType transactionType) {
        return transactionRepository.findByTransactionType(transactionType).stream()
                .map(transaction -> TransactionDto.builder()
                        .id(transaction.getId())
                        .walletId(transaction.getWallet().getId())
                        .amount(transaction.getAmount())
                        .transactionType(transaction.getTransactionType())
                        .status(transaction.getStatus())
                        .balanceAfterTransaction(transaction.getBalanceAfterTransaction())
                        .transactionDate(transaction.getTransactionDate())
                        .build())
                .toList();
    }

}
