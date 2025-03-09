package com.github.io.bank_wallet.controller;

import java.util.Map;
import java.util.Set;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.io.bank_wallet.dto.AuthRequest;
import com.github.io.bank_wallet.entity.User;
import com.github.io.bank_wallet.enums.Roles;
import com.github.io.bank_wallet.response.AuthResponse;
import com.github.io.bank_wallet.service.UserService;
import com.github.io.bank_wallet.utils.JwtUtil;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest authRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.username(), authRequest.password())
        );

        UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.username());
        String accessToken = jwtUtil.generateToken(userDetails);
        String refreshToken = jwtUtil.generateRefreshToken(userDetails);

        // Extract role from the user details (casting to User to access the role)
        Roles role = ((User) userDetails).getRole().iterator().next(); // Assuming User class has getRoles method

        return new AuthResponse(accessToken, refreshToken, userDetails.getUsername(), role);
    }

    @PostMapping("/refresh")
    public AuthResponse refreshToken(@RequestBody Map<String, String> request) {
        String refreshToken = request.get("refreshToken");
        if (refreshToken == null) {
            throw new IllegalArgumentException("Refresh Token is required");
        }

        String email = jwtUtil.extractUsername(refreshToken);
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);

        if (!jwtUtil.validateToken(refreshToken, userDetails)) {
            throw new IllegalArgumentException("Invalid Refresh Token");
        }

        String newAccessToken = jwtUtil.generateToken(userDetails);

        return new AuthResponse(newAccessToken, refreshToken, email, ((User) userDetails).getRole().iterator().next());
    }

}
