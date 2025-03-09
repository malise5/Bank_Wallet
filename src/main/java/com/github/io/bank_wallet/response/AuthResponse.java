package com.github.io.bank_wallet.response;

import javax.management.relation.Role;

public record AuthResponse(String token, String email, Role role) {
    
}
