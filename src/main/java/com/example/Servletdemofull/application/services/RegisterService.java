package com.example.Servletdemofull.application.services;

import com.example.Servletdemofull.domain.user.RoleEnum;
import com.example.Servletdemofull.config.JWT.JwtService;
import com.example.Servletdemofull.application.utils.UserExistInDb;
import com.example.Servletdemofull.infrastructure.output.entity.User;
import com.example.Servletdemofull.infrastructure.output.repository.UserRepository;
import com.example.Servletdemofull.infrastructure.input.rest.dtos.RegisterRequestDto;
import com.example.Servletdemofull.infrastructure.input.rest.dtos.AuthenticationResponseDto;

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
