package com.github.io.bank_wallet.dto;

import java.math.BigDecimal;

import lombok.Builder;

@Builder
public record WalletDto(Long id,Long userId,BigDecimal balance) {
    
}
