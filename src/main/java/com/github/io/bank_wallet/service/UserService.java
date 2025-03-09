package com.github.io.bank_wallet.service;

import java.util.List;

import com.github.io.bank_wallet.dto.UserDto;

public interface UserService {
    UserDto getUserByEmail(String email);
    UserDto createUser(UserDto userDto);
    UserDto updateUser(Long id, UserDto userDto);
    void deleteUser(Long id);
    List<UserDto> getAllUsers();
}
