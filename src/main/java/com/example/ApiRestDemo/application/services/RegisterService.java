package com.example.ApiRestDemo.application.services;

import com.example.ApiRestDemo.domain.user.RoleEnum;
import com.example.ApiRestDemo.config.JWT.JwtService;
import com.example.ApiRestDemo.application.utils.UserExistInDb;
import com.example.ApiRestDemo.infrastructure.output.entity.User;
import com.example.ApiRestDemo.infrastructure.output.repository.UserRepository;
import com.example.ApiRestDemo.infrastructure.input.rest.dtos.RegisterRequestDto;
import com.example.ApiRestDemo.infrastructure.input.rest.dtos.AuthenticationResponseDto;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RegisterService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserExistInDb userExistInDb;

    public AuthenticationResponseDto register(RegisterRequestDto requestDto) {

        if (userExistInDb.ifUserExist(requestDto.getEmail())) return AuthenticationResponseDto.builder()
                .token("User already exist")
                .build();

        var user = User.builder()
                .firstname(requestDto.getFirstname())
                .lastname(requestDto.getLastname())
                .email(requestDto.getEmail())
                .password(passwordEncoder.encode(requestDto.getPassword()))
                .role(RoleEnum.ADMIN)
                .build();
        repository.save(user);

        var jwtToken = jwtService.generateToken(user);
            return AuthenticationResponseDto.builder()
                    .token(jwtToken)
                    .build();
    }
}
