package com.github.io.bank_wallet.controller;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.io.bank_wallet.dto.WalletDto;
import com.github.io.bank_wallet.service.WalletService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("api/v1/wallet")
public class WalletController {

    private WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @PostMapping("/{userId}")
    public ResponseEntity<WalletDto> createWallet(@PathVariable Long userId) {
        WalletDto walletDto = walletService.createWallet(userId);
        return ResponseEntity.status(201).body(walletDto);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<WalletDto> getWallet(@PathVariable Long userId) {
        Optional<WalletDto> wallet= walletService.getWalletByUserId(userId);
        return wallet.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/{walletId}/balance")
    public ResponseEntity<WalletDto> updateBalance(@PathVariable Long walletId, @RequestParam BigDecimal amount) {
        WalletDto wallet = walletService.updateBalance(walletId, amount);
        return ResponseEntity.ok(wallet);
    }
    
    
}
