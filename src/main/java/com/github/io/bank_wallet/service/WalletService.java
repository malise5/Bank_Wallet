package com.github.io.bank_wallet.service;

import java.math.BigDecimal;
import java.util.Optional;

import com.github.io.bank_wallet.dto.WalletDto;

public interface WalletService {
    WalletDto createWallet(Long userId);
    Optional<WalletDto> getWalletByUserId(Long userId);
    WalletDto updateBalance(Long walletId, BigDecimal amount);
}
