package com.codingShuttle.Ashish.SpringSecurity.controllers;

import com.codingShuttle.Ashish.SpringSecurity.dto.LoginDto;
import com.codingShuttle.Ashish.SpringSecurity.dto.LoginResponseDto;
import com.codingShuttle.Ashish.SpringSecurity.dto.SignUpDto;
import com.codingShuttle.Ashish.SpringSecurity.dto.UserDto;
import com.codingShuttle.Ashish.SpringSecurity.services.AuthService;
import com.codingShuttle.Ashish.SpringSecurity.services.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthService authService;

    @Value("${deploy.env}")
    private String deployEnv;

    @PostMapping("/signup")
    public ResponseEntity<UserDto> signUp(@RequestBody SignUpDto signUpDto){
        UserDto userDto = userService.signUp(signUpDto);
        return ResponseEntity.ok(userDto);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginDto loginDto, HttpServletResponse response){
        LoginResponseDto loginResponseDto = authService.login(loginDto);

        Cookie cookie = new Cookie("refreshToken", loginResponseDto.getRefreshToken());
        cookie.setHttpOnly(true); // set this so that cookie can't be accessed via JS.
       // cookie.setSecure(true); // set this true when using HTTPS, we can toggle this on the basis of our developer/production. We will be in production mode when we will get our own Domain which provide us the "TLS" certificate which power "S" inside HTTPS.
        cookie.setSecure("production".equals(deployEnv));
        response.addCookie(cookie);

        return ResponseEntity.ok(loginResponseDto);
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponseDto> refresh(HttpServletRequest request){

        // 1st check if refreshToken is even present inside the cookies or not.
        String refreshToken = Arrays.stream(request.getCookies()).
                filter(cookie -> "refreshToken".equals(cookie.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElseThrow(()-> new AuthenticationServiceException("Refresh token not found inside the cookie"));

        // 2nd generate the access and refreshToken again.
        LoginResponseDto loginResponseDto = authService.refresh(refreshToken);
        return ResponseEntity.ok(loginResponseDto);
    }

}
