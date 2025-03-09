package com.github.io.bank_wallet.serviceImpl;

import java.util.List;
import java.util.Optional;

import com.github.io.bank_wallet.dto.UserDto;
import com.github.io.bank_wallet.entity.User;
import com.github.io.bank_wallet.repository.UserRepository;
import com.github.io.bank_wallet.service.UserService;

public class UserServiceImpl implements UserService{

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDto getUserByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isPresent()) {
            return UserDto.builder()
                    .email(user.get().getEmail())
                    .firstName(user.get().getFirstname())
                    .lastName(user.get().getLastname())
                    .role(user.get().getRole())
                    .build();
        }
        return null;
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createUser'");
    }

    @Override
    public UserDto updateUser(Long id, UserDto userDto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateUser'");
    }

    @Override
    public void deleteUser(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteUser'");
    }

    @Override
    public List<UserDto> getAllUsers() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllUsers'");
    }

    
}
