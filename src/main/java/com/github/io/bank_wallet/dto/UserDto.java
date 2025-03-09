package com.github.io.bank_wallet.dto;

import com.github.io.bank_wallet.enums.Roles;

import lombok.Builder;

@Builder
public record UserDto( String firstName,String lastName, String email,Roles role) {
}
