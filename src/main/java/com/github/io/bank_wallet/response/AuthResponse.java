package com.github.io.bank_wallet.response;


import com.github.io.bank_wallet.enums.Roles;

public record AuthResponse(String accessToken,String refreshToken, String email, Roles role) {
    
}
