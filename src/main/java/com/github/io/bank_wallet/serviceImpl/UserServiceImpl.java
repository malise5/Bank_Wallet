package com.github.io.bank_wallet.serviceImpl;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.security.access.method.P;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.github.io.bank_wallet.dto.UserDto;
import com.github.io.bank_wallet.entity.User;
import com.github.io.bank_wallet.enums.Roles;
import com.github.io.bank_wallet.repository.UserRepository;
import com.github.io.bank_wallet.service.UserService;

@Service
public class UserServiceImpl implements UserService{

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    public UserDto registerUser(UserDto userDto, String rawPassword) {
        String encodedPassword = passwordEncoder.encode(rawPassword);
        User user = User.builder()
                            .firstname(userDto.firstName())
                            .lastname(userDto.lastName())
                            .username(userDto.username())
                            .email(userDto.email())
                            .password(encodedPassword)
                            .role(userDto.role())
                            .isVerified(false)
                            .build();
        User savedUser = userRepository.save(user);
        return UserDto.builder()
                        .firstName(savedUser.getFirstname())
                        .lastName(savedUser.getLastname())
                        .username(savedUser.getUsername())
                        .email(savedUser.getEmail())
                        .role(savedUser.getRole())
                        .isVerified(savedUser.isVerified())
                        .build();
    }

    @Override
    public Optional<UserDto> findByEmail(String email) {
        return userRepository.findByEmail(email)
                            .map(user -> UserDto.builder()
                                                .firstName(user.getFirstname())
                                                .lastName(user.getLastname())
                                                .username(user.getUsername())
                                                .email(user.getEmail())
                                                .role(user.getRole())
                                                .isVerified(user.isVerified())
                                                .build());
    }

    @Override
    public UserDto updateUser(Long id, UserDto userDto) {
        return userRepository.findById(id)
                            .map(user -> {
                                user.setFirstname(userDto.firstName());
                                user.setLastname(userDto.lastName());
                                user.setUsername(userDto.username());
                                user.setEmail(userDto.email());
                                user.setRole(userDto.role());
                                User updatedUser = userRepository.save(user);
                                return UserDto.builder()
                                                .firstName(updatedUser.getFirstname())
                                                .lastName(updatedUser.getLastname())
                                                .username(updatedUser.getUsername())
                                                .email(updatedUser.getEmail())
                                                .role(updatedUser.getRole())
                                                .build();
                            })
                            .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    @Override
    public boolean deleteUser(Long id) {
        return userRepository.findById(id)
                            .map(user -> {
                                userRepository.delete(user);
                                return true;
                            })
                            .orElse(false);
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll()
                            .stream()
                            .map(user -> UserDto.builder()
                                                .firstName(user.getFirstname())
                                                .lastName(user.getLastname())
                                                .username(user.getUsername())
                                                .email(user.getEmail())
                                                .role(user.getRole())
                                                .isVerified(user.isVerified())
                                                .build())
                            .toList();
    }



    
}
