package com.github.io.bank_wallet.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.io.bank_wallet.dto.TransactionDto;
import com.github.io.bank_wallet.enums.TransactionType;
import com.github.io.bank_wallet.service.TransactionService;

@RestController
@RequestMapping("api/v1/transaction")
public class TransactionController {

    private TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/{walletId}")
    public ResponseEntity<TransactionDto> createTransaction(@PathVariable Long walletId,
            @RequestParam BigDecimal amount, @RequestParam TransactionType transactionType) {
        TransactionDto transactionDto = transactionService.createTransaction(walletId, amount, transactionType);
        return ResponseEntity.status(201).body(transactionDto);
    }

    @GetMapping("/{walletId}")
    public ResponseEntity<List<TransactionDto>> getTransactionsByWalletId(@PathVariable Long walletId) {
        List<TransactionDto> transactions = transactionService.getTransactionsByWalletId(walletId);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<TransactionDto>> getTransactionsByTransactionType(@PathVariable TransactionType type) {
        List<TransactionDto> transactions = transactionService.getTransactionsByTransactionType(type);
        return ResponseEntity.ok(transactions);
    }

}
