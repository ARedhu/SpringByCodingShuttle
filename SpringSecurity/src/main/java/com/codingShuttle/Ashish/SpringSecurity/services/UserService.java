package com.codingShuttle.Ashish.SpringSecurity.services;

import com.codingShuttle.Ashish.SpringSecurity.dto.LoginDto;
import com.codingShuttle.Ashish.SpringSecurity.dto.SignUpDto;
import com.codingShuttle.Ashish.SpringSecurity.dto.UserDto;
import com.codingShuttle.Ashish.SpringSecurity.entities.User;
import com.codingShuttle.Ashish.SpringSecurity.exceptions.ResourceNotFoundException;
import com.codingShuttle.Ashish.SpringSecurity.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new ResourceNotFoundException("User with email "+username+" not found"));
    }

    public UserDto signUp(SignUpDto signUpDto) {
        Optional<User> user = userRepository.findByEmail(signUpDto.getEmail());
        if(user.isPresent()){
            throw new BadCredentialsException("User with email: "+signUpDto.getEmail()+" already exists");
        }

        User tobeCreated = modelMapper.map(signUpDto, User.class);
        tobeCreated.setPassword(passwordEncoder.encode(tobeCreated.getPassword()));

        User savedUser = userRepository.save(tobeCreated);
        return modelMapper.map(savedUser, UserDto.class);


    }

}
