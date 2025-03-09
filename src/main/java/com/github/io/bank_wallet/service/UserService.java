package com.github.io.bank_wallet.service;

import java.util.List;
import java.util.Optional;

import com.github.io.bank_wallet.dto.UserDto;


public interface UserService {
    
    UserDto registerUser(UserDto userDto, String rawPassword);  // Separate registration

    Optional<UserDto> findByEmail(String email);  // Use Optional for safety

    UserDto updateUser(Long id, UserDto userDto);

    boolean deleteUser(Long id);  // Return boolean instead of void

    List<UserDto> getAllUsers();
}
