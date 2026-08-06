package com.codingShuttle.Ashish.SpringSecurity.services;

import com.codingShuttle.Ashish.SpringSecurity.dto.LoginDto;
import com.codingShuttle.Ashish.SpringSecurity.dto.LoginResponseDto;
import com.codingShuttle.Ashish.SpringSecurity.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager; // It's job is only one thing: Verify whether the username/password is correct or not.
    private final JwtService jwtService;
    private final UserService userService;

    public LoginResponseDto login(LoginDto loginDto) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())
        );

        User user = (User) authentication.getPrincipal();
        String accessToken =  jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        return new LoginResponseDto(user.getId(), accessToken, refreshToken);
    }

    public LoginResponseDto refresh(String refreshToken){
        // Step-1: Check if refreshToken is vaild or not.
        Long userId = jwtService.getUserIdFromToken(refreshToken);

        // Step-2:
        User user = userService.getUserById(userId);
        String accessToken =  jwtService.generateAccessToken(user);
        return new LoginResponseDto(user.getId(), accessToken, refreshToken);
    }
}
