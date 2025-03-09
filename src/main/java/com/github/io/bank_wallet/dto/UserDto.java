package com.github.io.bank_wallet.dto;

import java.util.Set;

import com.github.io.bank_wallet.enums.Roles;

import lombok.Builder;

// @Builder
// public record UserDto( String firstName,String lastName, String email,Roles role) {
// }

@Builder
public record UserDto(String firstName, String lastName, String username, String email, Set<Roles> role, boolean isVerified) {
}
