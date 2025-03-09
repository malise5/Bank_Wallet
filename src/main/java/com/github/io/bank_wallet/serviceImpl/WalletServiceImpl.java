package com.github.io.bank_wallet.serviceImpl;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.github.io.bank_wallet.dto.WalletDto;
import com.github.io.bank_wallet.entity.User;
import com.github.io.bank_wallet.entity.Wallet;
import com.github.io.bank_wallet.repository.UserRepository;
import com.github.io.bank_wallet.repository.WalletRepository;
import com.github.io.bank_wallet.service.WalletService;

@Service
public class WalletServiceImpl implements WalletService{

    private WalletRepository walletRepository;
    private UserRepository userRepository;

    public WalletServiceImpl(WalletRepository walletRepository, UserRepository userRepository) {
        this.walletRepository = walletRepository;
        this.userRepository = userRepository;
    }

    @Override
    public WalletDto createWallet(Long userId) {

        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        Wallet wallet = walletRepository.save(Wallet.builder()
                .user(user)
                .balance(BigDecimal.ZERO)
                .build());
        
        return WalletDto.builder()
                .id(wallet.getId())
                .userId(wallet.getUser().getId())
                .balance(wallet.getBalance())
                .build();

    }

    @Override
    public Optional<WalletDto> getWalletByUserId(Long userId) {
        return walletRepository.findByUserId(userId).map(wallet -> WalletDto.builder()
                .id(wallet.getId())
                .userId(wallet.getUser().getId())
                .balance(wallet.getBalance())
                .build());
         

    }

    @Override
    public WalletDto updateBalance(Long walletId, BigDecimal amount) {
        Wallet wallet = walletRepository.findById(walletId).orElseThrow(() -> new RuntimeException("Wallet not found"));
        wallet.setBalance(wallet.getBalance().add(amount));
        walletRepository.save(wallet);
        return WalletDto.builder()
                .id(wallet.getId())
                .userId(wallet.getUser().getId())
                .balance(wallet.getBalance())
                .build();
    }
    
}
