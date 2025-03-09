package com.github.io.bank_wallet.response;


import com.github.io.bank_wallet.enums.Roles;

public record AuthResponse(String token, String email, Roles role) {
    
}
