package com.github.io.bank_wallet.controller;

import java.util.Map;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.io.bank_wallet.dto.AuthRequest;
import com.github.io.bank_wallet.dto.UserDto;
import com.github.io.bank_wallet.entity.User;
import com.github.io.bank_wallet.enums.Roles;
import com.github.io.bank_wallet.response.AuthResponse;
import com.github.io.bank_wallet.service.UserService;
import com.github.io.bank_wallet.serviceImpl.UserServiceImpl;
import com.github.io.bank_wallet.utils.JwtUtil;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;
    private final UserServiceImpl userService;
    private final BCryptPasswordEncoder passwordEncoder;



     // Registration endpoint
     @PostMapping("/register")
     public ResponseEntity<AuthResponse> register(@RequestBody UserDto userDto) {

        // Step 1: Register user using UserService (password is already included in UserDto)
        UserDto savedUser = userService.registerUser(userDto, userDto.password());

        return ResponseEntity.status(HttpStatus.CREATED).body(null);
     }


    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest authRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.username(), authRequest.password())
        );

        UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.username());
        String accessToken = jwtUtil.generateToken(userDetails);
        String refreshToken = jwtUtil.generateRefreshToken(userDetails);

        // Extract role from the user details (casting to User to access the role)
        Roles role = ((User) userDetails).getRole();

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

        return new AuthResponse(newAccessToken, refreshToken, email, ((User) userDetails).getRole());
    }

}
