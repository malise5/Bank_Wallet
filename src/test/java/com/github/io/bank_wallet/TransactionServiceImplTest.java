package com.github.io.bank_wallet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.github.io.bank_wallet.dto.TransactionDto;
import com.github.io.bank_wallet.entity.Transaction;
import com.github.io.bank_wallet.entity.User;
import com.github.io.bank_wallet.entity.Wallet;
import com.github.io.bank_wallet.enums.StatusType;
import com.github.io.bank_wallet.enums.TransactionType;
import com.github.io.bank_wallet.repository.TransactionRepository;
import com.github.io.bank_wallet.repository.UserRepository;
import com.github.io.bank_wallet.repository.WalletRepository;
import com.github.io.bank_wallet.serviceImpl.TransactionServiceImpl;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private WalletRepository walletRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    private Wallet wallet;
    private Transaction transaction;

    @BeforeEach
    public void setUp() {
        wallet = Wallet.builder()
                .id(1L)
                .balance(BigDecimal.TEN)
                .user(User.builder().id(1L).build())
                .build();
            
        transaction = Transaction.builder()
                .id(1L)
                .wallet(wallet)
                .amount(BigDecimal.valueOf(100))
                .transactionType(TransactionType.DEPOSIT)
                .status(StatusType.COMPLETED)
                .balanceAfterTransaction(BigDecimal.valueOf(1100))
                .transactionDate(LocalDateTime.now())
                .build();
    }

    @Test
    public void testGetTransactionsByWalletId() {
        when(transactionRepository.findByWalletId(1L)).thenReturn(Arrays.asList(transaction));

        List<TransactionDto> transactions = transactionService.getTransactionsByWalletId(1L);

        assertEquals(1, transactions.size());
        assertEquals(transaction.getId(), transactions.get(0).id());
        assertEquals(transaction.getWallet().getId(), transactions.get(0).walletId());
        assertEquals(transaction.getAmount(), transactions.get(0).amount());
        assertEquals(transaction.getTransactionType(), transactions.get(0).transactionType());
        assertEquals(transaction.getStatus(), transactions.get(0).status());
        assertEquals(transaction.getBalanceAfterTransaction(), transactions.get(0).balanceAfterTransaction());
        assertEquals(transaction.getTransactionDate(), transactions.get(0).transactionDate());
    }
    
}
