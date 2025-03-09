package com.github.io.bank_wallet.service;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.github.io.bank_wallet.entity.User;
import com.github.io.bank_wallet.enums.Roles;
import com.github.io.bank_wallet.repository.UserRepository;

public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new org.springframework.security.core.userdetails.User(
            user.getEmail(),
            user.getPassword(),
            user.getRole().stream()
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                    .toList()
    );
    }

    // Method to convert role to authorities
    private Collection<SimpleGrantedAuthority> getAuthorities(Roles role) {
        // Assuming Roles is an enum, and you want to add the "ROLE_" prefix as per Spring Security convention
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }
    
}
