package com.codingShuttle.Ashish.SpringSecurity.services;

import com.codingShuttle.Ashish.SpringSecurity.dto.LoginDto;
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

    public String login(LoginDto loginDto) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())
        );

        User user = (User) authentication.getPrincipal();
        return jwtService.generateToken(user);

    }
}
