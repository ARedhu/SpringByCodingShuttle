package com.codingShuttle.Ashish.SpringSecurity.controllers;

import com.codingShuttle.Ashish.SpringSecurity.dto.LoginDto;
import com.codingShuttle.Ashish.SpringSecurity.dto.SignUpDto;
import com.codingShuttle.Ashish.SpringSecurity.dto.UserDto;
import com.codingShuttle.Ashish.SpringSecurity.services.AuthService;
import com.codingShuttle.Ashish.SpringSecurity.services.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<UserDto> signUp(@RequestBody SignUpDto signUpDto){
        UserDto userDto = userService.signUp(signUpDto);
        return ResponseEntity.ok(userDto);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto, HttpServletResponse response){
        String token = authService.login(loginDto);

        Cookie cookie = new Cookie("token", token);
        cookie.setHttpOnly(true); // set this so that cookie can't be accessed via JS.
       // cookie.setSecure(true); // set this true when using HTTPS, we can toggle this on the basis of our developer/production.
        response.addCookie(cookie);

        return ResponseEntity.ok(token);
    }

}
