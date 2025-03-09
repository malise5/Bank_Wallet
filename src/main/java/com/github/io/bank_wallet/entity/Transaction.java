package com.github.io.bank_wallet.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.github.io.bank_wallet.enums.StatusType;
import com.github.io.bank_wallet.enums.TransactionType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "transactions")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "wallet_id", nullable = false)
    private Wallet wallet;

    @Column(nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType transactionType; // "DEPOSIT", "WITHDRAWAL", "TRANSFER"

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusType status; // "PENDING", "COMPLETED", "FAILED"

    @Column(nullable = false)
    private BigDecimal balanceAfterTransaction;


    @Column(nullable = false)
    private LocalDateTime transactionDate;

    @PrePersist
    protected void onCreate() {
        transactionDate = LocalDateTime.now();
    }
    
}
